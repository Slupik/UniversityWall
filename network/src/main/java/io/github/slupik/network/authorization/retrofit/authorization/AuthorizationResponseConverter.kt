/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization.retrofit.authorization

import io.github.slupik.model.authorization.authorizer.AuthorizationResult
import io.github.slupik.network.ResponseConverter
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class AuthorizationResponseConverter @Inject constructor() :
    ResponseConverter<AuthorizationResponse, AuthorizationResult>() {

    override fun convert(response: AuthorizationResponse): AuthorizationResult {
        if (!response.validLogin) {
            return AuthorizationResult.INVALID_LOGIN
        }
        if (!response.validPassword) {
            return AuthorizationResult.INVALID_PASSWORD
        }
        if (response.token.isEmpty()) {
            return AuthorizationResult.CONNECTION_ERROR
        }
        return AuthorizationResult.SUCCESS
    }

}