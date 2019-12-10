/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization.authorizer

import io.github.slupik.model.authorization.authorizer.AuthorizationResult
import io.github.slupik.model.authorization.authorizer.Authorizer
import io.github.slupik.model.authorization.credentials.CredentialSaver
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.authorization.retrofit.AuthorizationResponse
import io.github.slupik.network.authorization.retrofit.AuthorizationService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class ServerAuthorizer @Inject constructor(
    private val service: AuthorizationService,
    private val saver: CredentialSaver,
    private val converter: ResponseConverter<AuthorizationResponse, AuthorizationResult>
): Authorizer {

    override fun logIn(login: String, password: String): Single<AuthorizationResult> =
        try {
            service.authorize(login, password)
                .doOnSuccess { response ->
                    if(response.token.isNotEmpty()) {
                        saver.save(login, password)
                    }
                }
                .map(converter::convert)
                .onErrorReturn {
                    it.printStackTrace()
                    AuthorizationResult.CONNECTION_ERROR
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Single.just(AuthorizationResult.CONNECTION_ERROR)
        }

}