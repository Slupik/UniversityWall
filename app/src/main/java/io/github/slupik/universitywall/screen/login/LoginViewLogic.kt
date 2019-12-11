package io.github.slupik.universitywall.screen.login

import io.github.slupik.model.authorization.authorizer.AuthorizationResult
import io.github.slupik.model.authorization.authorizer.Authorizer
import io.github.slupik.model.authorization.credentials.INVALID_LOGIN
import io.github.slupik.model.authorization.credentials.INVALID_PASSWORD
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginViewLogic @Inject constructor(
    private val authorizer: Authorizer
) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var viewModel: LoginViewModel

    fun inject(viewModel: LoginViewModel) {
        this.viewModel = viewModel
    }

    fun onLogIn() {
        if(viewModel.login.value.isNullOrBlank()) {
            viewModel.viewState.postValue(
                WrongLoginViewState()
            )
            return
        }
        if(viewModel.password.value.isNullOrBlank()) {
            viewModel.viewState.postValue(
                WrongPasswordViewState()
            )
            return
        }

        viewModel.viewState.postValue(LoadingDataViewState())

        authorizer.logIn(
            viewModel.login.value ?: INVALID_LOGIN,
            viewModel.password.value ?: INVALID_PASSWORD
        ).subscribe { result ->
            viewModel.viewState.postValue(StartViewState())
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
                    //TODO move to main screen
                }
            }
        }.remember()
    }

    private fun Disposable.remember() {
        compositeDisposable.add(this)
    }

}
