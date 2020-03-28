/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.login

import androidx.lifecycle.MutableLiveData
import com.squareup.inject.assisted.AssistedInject
import io.github.slupik.model.authorization.INVALID_LOGIN
import io.github.slupik.model.authorization.INVALID_PASSWORD
import io.github.slupik.model.authorization.authorizer.AuthorizationResult
import io.github.slupik.model.authorization.authorizer.Authorizer
import io.github.slupik.model.authorization.credentials.CredentialsValidator
import io.github.slupik.model.utils.subscribeOnIOThread
import io.github.slupik.universitywall.utils.observeOnMainThread
import io.github.slupik.universitywall.viewmodel.ViewModel
import io.reactivex.rxkotlin.subscribeBy

class LoginViewModel @AssistedInject constructor(
    private val authorizer: Authorizer,
    private val validator: CredentialsValidator
) : ViewModel() {

    val login: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val viewState: MutableLiveData<LoginViewState> by lazy {
        MutableLiveData<LoginViewState>()
    }
    val navigation: MutableLiveData<NavigationCommand> by lazy {
        MutableLiveData<NavigationCommand>()
    }

    init {
        viewState.postValue(StartViewState())
    }

    fun onLogIn() {
        if (validator.isValidLogin(login.value).not()) {
            viewState.postValue(WrongLoginViewState())
            return
        }
        if (validator.isValidPassword(password.value).not()) {
            viewState.postValue(WrongPasswordViewState())
            return
        }

        viewState.postValue(LoadingDataViewState())

        authorizer.logIn(
            login.value ?: INVALID_LOGIN,
            password.value ?: INVALID_PASSWORD
        )
            .subscribeOnIOThread()
            .observeOnMainThread()
            .subscribeBy(
                onSuccess = { result ->
                    val newViewState = AuthorizationResultToViewState.convert(result);
                    viewState.postValue(newViewState)
                    if (result == AuthorizationResult.SUCCESS) {
                        navigation.postValue(NavigationCommand.MESSAGES_SCREEN)
                    }
                },
                onError = {
                    it.printStackTrace()
                    viewState.postValue(
                        ConnectionErrorViewState()
                    )
                }
            ).remember()
    }

    fun onRegistration() {
        navigation.postValue(NavigationCommand.REGISTRATION_SCREEN)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(): LoginViewModel
    }

}
