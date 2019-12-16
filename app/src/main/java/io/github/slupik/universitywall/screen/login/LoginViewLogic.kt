package io.github.slupik.universitywall.screen.login

import io.github.slupik.model.authorization.INVALID_LOGIN
import io.github.slupik.model.authorization.INVALID_PASSWORD
import io.github.slupik.model.authorization.authorizer.AuthorizationResult
import io.github.slupik.model.authorization.authorizer.Authorizer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewLogic @Inject constructor(
    private val authorizer: Authorizer
) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var viewModel: LoginViewModel
    private lateinit var navigation: GraphController

    fun inject(viewModel: LoginViewModel) {
        this.viewModel = viewModel
    }

    fun inject(navigation: GraphController) {
        this.navigation = navigation
    }

    fun onLogIn() {
        if (viewModel.login.value.isNullOrBlank()) {
            viewModel.viewState.postValue(
                WrongLoginViewState()
            )
            return
        }
        if (viewModel.password.value.isNullOrBlank() || (viewModel.password.value as String).length <= 4) {
            viewModel.viewState.postValue(
                WrongPasswordViewState()
            )
            return
        }

        viewModel.viewState.postValue(LoadingDataViewState())

        authorizer.logIn(
            viewModel.login.value ?: INVALID_LOGIN,
            viewModel.password.value ?: INVALID_PASSWORD
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { result ->
                    when (result!!) {
                        AuthorizationResult.CONNECTION_ERROR -> {
                            viewModel.viewState.postValue(
                                ConnectionErrorViewState()
                            )
                        }
                        AuthorizationResult.INVALID_LOGIN -> {
                            viewModel.viewState.postValue(
                                WrongLoginViewState()
                            )
                        }
                        AuthorizationResult.INVALID_PASSWORD -> {
                            viewModel.viewState.postValue(
                                WrongPasswordViewState()
                            )
                        }
                        AuthorizationResult.SUCCESS -> {
                            viewModel.viewState.postValue(StartViewState())
                            navigation.moveToMessagesScreen()
                        }
                    }
                },
                onError = {
                    viewModel.viewState.postValue(
                        ConnectionErrorViewState()
                    )
                }
            ).remember()
    }

    private fun Disposable.remember() {
        compositeDisposable.add(this)
    }

    fun onRegistration() {
        navigation.moveToRegistrationScreen()
    }

}
