/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.authorization.credentials

import io.github.slupik.model.authorization.DISPLAY_NAME_MIN_LENGTH
import io.github.slupik.model.authorization.LOGIN_MIN_LENGTH
import io.github.slupik.model.authorization.PASSWORD_MIN_LENGTH
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 28.03.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class ServerCredentialsValidator @Inject constructor() : CredentialsValidator {

    override fun isValidLogin(login: String?): Boolean =
        login != null && login.length >= LOGIN_MIN_LENGTH

    override fun isValidPassword(password: String?): Boolean =
        password != null && password.length >= PASSWORD_MIN_LENGTH

    override fun isValidDisplayName(displayName: String?): Boolean =
        displayName != null && displayName.length >= DISPLAY_NAME_MIN_LENGTH

}