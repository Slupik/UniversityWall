/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.registration

import android.app.AlertDialog
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import io.github.slupik.universitywall.R
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 11.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class DialogedRegistrationErrorHandler @Inject constructor(
    private val activity: FragmentActivity
) : RegistrationErrorHandler {

    override fun onError(type: ErrorType) {
        TODO("Implement!")
    }

    override fun onConnectionError() {
        showErrorInfo(
            R.string.registration_error_title,
            R.string.connection_error_registration
        )
    }

    override fun onLoginAlreadyExists() {
        showErrorInfo(
            R.string.registration_error_title,
            R.string.connection_error_login_exists
        )
    }

    override fun onInappropriatePassword() {
        showErrorInfo(
            R.string.registration_error_title,
            R.string.connection_error_inappropriate_password
        )
    }

    override fun onDifferentPasswords() {
        showErrorInfo(
            R.string.registration_error_title,
            R.string.connection_error_different_passwords
        )
    }

    override fun onEmptyFields(activity: FragmentActivity) {
        showErrorInfo(
            R.string.registration_error_title,
            R.string.connection_error_empty_fields
        )
    }

    private fun showErrorInfo(@StringRes title: Int, @StringRes info: Int) {
        AlertDialog.Builder(activity)
            .setTitle(getString(title))
            .setMessage(getString(info))
            .setPositiveButton(R.string.registration_error_ok, null)
            .show()
    }

    private fun getString(@StringRes id: Int): String =
        activity.getString(id)

}