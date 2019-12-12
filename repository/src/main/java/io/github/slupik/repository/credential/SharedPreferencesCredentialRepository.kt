/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.credential

import android.content.Context
import io.github.slupik.model.authorization.INVALID_LOGIN
import io.github.slupik.model.authorization.INVALID_PASSWORD
import io.github.slupik.model.authorization.credentials.CredentialSaver
import io.github.slupik.model.authorization.credentials.CredentialsProvider
import io.github.slupik.repository.R
import io.github.slupik.repository.sharedpreferences.SharedPreferencesRepository
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class SharedPreferencesCredentialRepository @Inject constructor(
    context: Context
) : SharedPreferencesRepository(context), CredentialSaver, CredentialsProvider {

    override val preferencesNameId: Int = R.string.preference_file_authorization

    override val login: String
        get() = getStringFromPreferences(R.string.saved_login, INVALID_LOGIN)

    override val password: String
        get() = getStringFromPreferences(R.string.saved_password, INVALID_PASSWORD)

    override fun save(login: String, password: String) {
        saveAsynchronously {
            with(it) {
                putString(R.string.saved_login, login)
                putString(R.string.saved_password, password)
            }
        }
    }

}
