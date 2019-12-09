package io.github.slupik.universitywall.screen.login

import io.github.slupik.model.authorization.authorizer.AuthorizationResult
import io.github.slupik.model.authorization.authorizer.Authorizer
import io.github.slupik.model.authorization.credentials.INVALID_LOGIN
import io.github.slupik.model.authorization.credentials.INVALID_PASSWORD
import javax.inject.Inject

class LoginViewLogic @Inject constructor(
    private val authorizer: Authorizer
) {

//    private lateinit var stateChanger: (LoginViewState) -> Unit
    private lateinit var viewModel: LoginViewModel
//
    fun inject(viewModel: LoginViewModel) {
        this.viewModel = viewModel
    }
//
//    fun inject(stateChanger: (LoginViewState)->Unit) {
//        this.stateChanger = stateChanger
//    }

    fun onLogIn() {
//        Log.d("BARCODE_T", "onLogIn")
        if(viewModel.login.value.isNullOrBlank()) {
//            Log.d("BARCODE_T", "isNullOrBlank 0")
            viewModel.viewState.postValue(
                WrongLoginViewState()
            )
//            Log.d("BARCODE_T", "isNullOrBlank 1")
            return
        }
        if(viewModel.password.value.isNullOrBlank()) {
            viewModel.viewState.postValue(
                WrongPasswordViewState()
            )
            return
        }

        viewModel.viewState.postValue(
            LoadingDataViewState()
        )

        authorizer.logIn(
            viewModel.login.value ?: INVALID_LOGIN,
            viewModel.password.value ?: INVALID_PASSWORD
        ).subscribe { result ->
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

                }
            }
        }
    }

}
