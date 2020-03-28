/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.login

import androidx.lifecycle.MutableLiveData
import com.squareup.inject.assisted.AssistedInject
import io.github.slupik.model.authorization.authorizer.Authorizer
import io.github.slupik.universitywall.viewmodel.ViewModel

class LoginViewModel @AssistedInject constructor(
    private val authorizer: Authorizer
) : ViewModel() {

    private lateinit var logic: LoginViewLogic

    val login: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val viewState: MutableLiveData<LoginViewState> by lazy {
        MutableLiveData<LoginViewState>()
    }

    init {
        viewState.postValue(StartViewState())
    }

    fun onLogIn() {
        logic.onLogIn()
    }

    fun onRegistration() {
        logic.onRegistration()
    }

    fun setLogic(viewLogic: LoginViewLogic) {
        this.logic = viewLogic
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(): LoginViewModel
    }

}
