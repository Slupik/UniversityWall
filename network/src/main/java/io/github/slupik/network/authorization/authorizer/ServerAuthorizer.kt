/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization.authorizer

import io.github.slupik.model.authorization.INVALID_LOGIN
import io.github.slupik.model.authorization.INVALID_PASSWORD
import io.github.slupik.model.authorization.authorizer.AuthorizationResult
import io.github.slupik.model.authorization.authorizer.Authorizer
import io.github.slupik.model.authorization.credentials.CredentialSaver
import io.github.slupik.model.authorization.state.AuthorizationState
import io.github.slupik.model.authorization.state.AuthorizationStatePublisher
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.authorization.retrofit.authorization.AuthorizationResponse
import io.github.slupik.network.authorization.retrofit.authorization.AuthorizationService
import io.github.slupik.network.authorization.token.TokenHolder
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

const val TOKEN_PREFIX = "Bearer "

class ServerAuthorizer @Inject constructor(
    private val service: AuthorizationService,
    private val saver: CredentialSaver,
    private val tokenHolder: TokenHolder,
    private val statePublisher: AuthorizationStatePublisher,
    private val converter: ResponseConverter<AuthorizationResponse, AuthorizationResult>
) : Authorizer {

    override fun logIn(login: String?, password: String?): Single<AuthorizationResult> =
        executeLogIn(
            login ?: INVALID_LOGIN,
            password ?: INVALID_PASSWORD
        )

    private fun executeLogIn(login: String, password: String): Single<AuthorizationResult> =
        try {
            service.authorize(login, password)
                .doOnSuccess { response ->
                    if (isValidToken(response.token)) {
                        saver.save(login, password)
                        tokenHolder.session = TOKEN_PREFIX + response.token
                        statePublisher.onNewState(AuthorizationState.LOGGED_IN)
                    }
                }
                .map(converter::convert)
                .onErrorReturn {
                    it.printStackTrace()
                    statePublisher.onNewState(AuthorizationState.LOGGED_OUT)
                    AuthorizationResult.CONNECTION_ERROR
                }
        } catch (e: Exception) {
            e.printStackTrace()
            statePublisher.onNewState(AuthorizationState.LOGGED_OUT)
            Single.just(AuthorizationResult.CONNECTION_ERROR)
        }

    private fun isValidToken(token: String): Boolean =
        token.isNotEmpty()

}