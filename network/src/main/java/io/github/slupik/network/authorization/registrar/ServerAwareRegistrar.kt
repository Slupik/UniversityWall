/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization.registrar

import io.github.slupik.model.authorization.credentials.CredentialSaver
import io.github.slupik.model.authorization.registration.Registrar
import io.github.slupik.model.authorization.registration.RegistrationResult
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.authorization.retrofit.registration.RegistrationResponse
import io.github.slupik.network.authorization.retrofit.registration.RegistrationService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 11.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class ServerAwareRegistrar @Inject constructor(
    private val service: RegistrationService,
    private val saver: CredentialSaver,
    private val converter: ResponseConverter<RegistrationResponse, RegistrationResult>
): Registrar {

    override fun register(
        login: String,
        password: String,
        displayName: String
    ): Single<RegistrationResult> =
        try {
            service.register(login, password, displayName)
                .doOnSuccess { response ->
                    if(response.token.isNotEmpty()) {
                        saver.save(login, password)
                    }
                }
                .map(converter::convert)
                .onErrorReturn {
                    it.printStackTrace()
                    RegistrationResult.CONNECTION_ERROR
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Single.just(RegistrationResult.CONNECTION_ERROR)
        }

}