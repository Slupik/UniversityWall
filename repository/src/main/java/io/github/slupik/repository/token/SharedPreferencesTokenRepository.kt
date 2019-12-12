/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.token

import android.content.Context
import io.github.slupik.model.authorization.INVALID_SESSION_TOKEN
import io.github.slupik.model.authorization.token.TokenRepository
import io.github.slupik.repository.R
import io.github.slupik.repository.sharedpreferences.SharedPreferencesRepository
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class SharedPreferencesTokenRepository @Inject constructor(
    context: Context
) : SharedPreferencesRepository(context), TokenRepository {

    override val preferencesNameId: Int = R.string.preference_file_authorization

    override val sessionToken: String
        get() = getStringFromPreferences(R.string.saved_session_token, INVALID_SESSION_TOKEN)

    override fun saveSessionToken(token: String) {
        saveSynchronously {
            with(it) {
                putString(R.string.saved_session_token, token)
            }
        }
    }

}
