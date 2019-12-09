/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.github.slupik.universitywall.viewmodel.ViewModel

class LoginViewModel : ViewModel() {

    init {
        Log.d("BARCODE_T", "INIT VM " + hashCode())
    }

    private lateinit var invalidator: () -> Unit
    private lateinit var logic: LoginViewLogic
//    private lateinit var stateChanger: (LoginViewState) -> Unit

    val login: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val viewState: MutableLiveData<LoginViewState> by lazy {
        MutableLiveData<LoginViewState>()
    }

    fun setupInvalidator(invalidator: () -> Unit) {
        this.invalidator = invalidator
    }

//    fun inject(stateChanger: (LoginViewState) -> Unit) {
//        this.stateChanger = stateChanger
//    }

    fun onLogIn() {
        invalidator.invoke()
        Log.d("BARCODE_T", "onLogIn")
        logic.onLogIn()
//        stateChanger(
//            WrongLoginViewState()
//        )
//        viewState.postValue(WrongLoginViewState())
//        login.postValue("test 2")
        invalidator.invoke()
    }

    fun setLogic(viewLogic: LoginViewLogic) {
        this.logic = viewLogic
    }

}
