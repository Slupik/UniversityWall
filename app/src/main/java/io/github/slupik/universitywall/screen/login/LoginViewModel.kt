/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.login

import androidx.lifecycle.MutableLiveData
import io.github.slupik.universitywall.viewmodel.ViewModel

class LoginViewModel : ViewModel() {

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

    fun onLogIn() {
        logic.onLogIn()
    }

    fun setLogic(viewLogic: LoginViewLogic) {
        this.logic = viewLogic
    }

}
