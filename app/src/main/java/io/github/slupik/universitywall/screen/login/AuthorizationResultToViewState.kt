/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.login

import io.github.slupik.model.authorization.authorizer.AuthorizationResult

/**
 * Created by Sebastian Witasik on 28.03.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
object AuthorizationResultToViewState {

    fun convert(result: AuthorizationResult) =
        when (result) {
            AuthorizationResult.CONNECTION_ERROR ->
                ConnectionErrorViewState()
            AuthorizationResult.SUCCESS ->
                StartViewState()
            AuthorizationResult.INVALID_LOGIN ->
                WrongLoginViewState()
            AuthorizationResult.INVALID_PASSWORD ->
                WrongPasswordViewState()
        }

}