/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.registration

import androidx.fragment.app.FragmentActivity
import javax.inject.Inject

class RegistrationViewLogic @Inject constructor(
    private val errorHandler: RegistrationErrorHandler
) {

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var activity: FragmentActivity

    fun inject(viewModel: RegistrationViewModel) {
        this.viewModel = viewModel
    }

    fun onRegister() {
        if (isAtLeastOneFieldEmpty()) {
            errorHandler.onEmptyFields(activity)
            return
        }
        if (viewModel.password.value != viewModel.repeatedPassword.value) {
            errorHandler.onDifferentPasswords()
            return
        }

        viewModel.viewState.postValue(LoadingDataViewState())
    }

    private fun isAtLeastOneFieldEmpty(): Boolean =
        viewModel.displayName.value.isNullOrBlank() ||
                viewModel.login.value.isNullOrBlank() ||
                viewModel.password.value.isNullOrEmpty() ||
                viewModel.repeatedPassword.value.isNullOrEmpty()

    fun inject(activity: FragmentActivity) {
        this.activity = activity
    }

}
