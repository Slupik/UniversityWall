/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.authorization.credentials

/**
 * Created by Sebastian Witasik on 28.03.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
interface CredentialsValidator {

    fun isValidLogin(login: String?): Boolean

    fun isValidPassword(password: String?): Boolean

    fun isValidDisplayName(displayName: String?): Boolean

}