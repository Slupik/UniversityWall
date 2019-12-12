/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization.retrofit.authorization

import io.reactivex.Single

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MockedAuthorizationService: AuthorizationService {

    override fun authorize(login: String, password: String): Single<AuthorizationResponse> =
        getValidConnection()

    private fun getValidConnection(): Single<AuthorizationResponse> =
        Single.just(
            AuthorizationResponse(
                validLogin = true,
                validPassword = true,
                token = "test-token"
            )
        )

    private fun getAllError(): Single<AuthorizationResponse> =
        Single.just(
            AuthorizationResponse(
                validLogin = false,
                validPassword = false,
                token = ""
            )
        )

    private fun getLoginError(): Single<AuthorizationResponse> =
        Single.just(
            AuthorizationResponse(
                validLogin = false,
                validPassword = true,
                token = ""
            )
        )

    private fun getPasswordError(): Single<AuthorizationResponse> =
        Single.just(
            AuthorizationResponse(
                validLogin = true,
                validPassword = false,
                token = ""
            )
        )

    private fun getConnectionError(): Single<AuthorizationResponse> =
        Single.just(
            AuthorizationResponse(
                validLogin = true,
                validPassword = true,
                token = ""
            )
        )

}