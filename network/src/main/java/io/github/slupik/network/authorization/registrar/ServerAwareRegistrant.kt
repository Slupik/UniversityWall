/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization.registrar

import io.github.slupik.model.authorization.INVALID_DISPLAY_NAME
import io.github.slupik.model.authorization.INVALID_LOGIN
import io.github.slupik.model.authorization.INVALID_PASSWORD
import io.github.slupik.model.authorization.credentials.CredentialSaver
import io.github.slupik.model.authorization.registration.Registrant
import io.github.slupik.model.authorization.registration.RegistrationResult
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.authorization.retrofit.registration.RegistrationResponse
import io.github.slupik.network.authorization.retrofit.registration.RegistrationService
import io.github.slupik.network.authorization.token.AuthorizationResponseHandler
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 11.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class ServerAwareRegistrant @Inject constructor(
    private val service: RegistrationService,
    private val saver: CredentialSaver,
    private val responseHandler: AuthorizationResponseHandler,
    private val converter: ResponseConverter<RegistrationResponse, RegistrationResult>
) : Registrant {

    override fun register(
        login: String?,
        password: String?,
        displayName: String?
    ): Single<RegistrationResult> =
        executeRegister(
            login ?: INVALID_LOGIN,
            password ?: INVALID_PASSWORD,
            displayName ?: INVALID_DISPLAY_NAME
        )

    private fun executeRegister(
        login: String,
        password: String,
        displayName: String
    ): Single<RegistrationResult> =
        try {
            service.register(login, password, displayName)
                .doOnSuccess { response ->
                    if (areCredentialsValid(response)) {
                        responseHandler.handleToken(response.token)
                        saver.save(login, password)
                    }
                }
                .map(converter::convert)
                .onErrorReturn {
                    responseHandler.handleError(it)
                    RegistrationResult.CONNECTION_ERROR
                }
        } catch (e: Exception) {
            responseHandler.handleError(e)
            Single.just(RegistrationResult.CONNECTION_ERROR)
        }

    private fun areCredentialsValid(response: RegistrationResponse?): Boolean =
        response != null && response.validLogin && response.validPassword

}