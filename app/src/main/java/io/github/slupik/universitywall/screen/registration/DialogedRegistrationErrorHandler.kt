/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.registration

import android.app.AlertDialog
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import io.github.slupik.model.authorization.DISPLAY_NAME_MIN_LENGTH
import io.github.slupik.model.authorization.LOGIN_MIN_LENGTH
import io.github.slupik.model.authorization.PASSWORD_MIN_LENGTH
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
        when (type) {
            ErrorType.CONNECTION_ERROR ->
                onConnectionError()
            ErrorType.LOGIN_ALREADY_EXISTS ->
                onLoginAlreadyExists()
            ErrorType.LOGIN_TOO_SHORT ->
                onTooShortLogin()
            ErrorType.WRONG_PASSWORD ->
                onWrongPassword()
            ErrorType.PASSWORD_NOT_MEETS_CRITERIA ->
                onPasswordNotMeetRequirements()
            ErrorType.DISPLAY_NAME_TOO_SHORT ->
                onDisplayNameTooShort()
            ErrorType.DIFFERENT_PASSWORDS ->
                onDifferentPasswords()
            ErrorType.EMPTY_FIELD ->
                onEmptyFields()
        }
    }

    private fun onConnectionError() {
        showErrorInfo(R.string.registration_connection_error)
    }

    private fun onLoginAlreadyExists() {
        showErrorInfo(R.string.registration_login_already_exists)
    }

    private fun onTooShortLogin() {
        showErrorInfo(
            String.format(
                getString(R.string.registration_too_short_login),
                LOGIN_MIN_LENGTH
            )
        )
    }

    private fun onWrongPassword() {
        showErrorInfo(R.string.registration_wrong_password)
    }

    private fun onPasswordNotMeetRequirements() {
        showErrorInfo(
            String.format(
                getString(R.string.registration_password_not_meet_requirements),
                PASSWORD_MIN_LENGTH
            )
        )
    }

    private fun onDifferentPasswords() {
        showErrorInfo(R.string.registration_different_passwords)
    }

    private fun onDisplayNameTooShort() {
        showErrorInfo(
            String.format(
                getString(R.string.registration_display_name_too_short),
                DISPLAY_NAME_MIN_LENGTH
            )
        )
    }

    private fun onEmptyFields() {
        showErrorInfo(R.string.registration_empty_fields)
    }

    private fun showErrorInfo(@StringRes info: Int) =
        showErrorInfo(R.string.registration_error_title, info)

    private fun showErrorInfo(info: String) =
        showErrorInfo(getString(R.string.registration_error_title), info)

    private fun showErrorInfo(@StringRes title: Int, @StringRes info: Int) =
        showErrorInfo(getString(title), getString(info))

    private fun showErrorInfo(title: String, info: String) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(info)
            .setPositiveButton(R.string.registration_error_ok, null)
            .show()
    }

    private fun getString(@StringRes id: Int): String =
        activity.getString(id)

}