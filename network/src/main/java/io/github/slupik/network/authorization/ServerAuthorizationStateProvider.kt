/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization

import io.github.slupik.model.authorization.credentials.CredentialsProvider
import io.github.slupik.model.authorization.state.AuthorizationStateProvider
import io.github.slupik.network.authorization.token.INVALID_TOKEN
import io.github.slupik.network.authorization.token.TokenHolder
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Singleton
class ServerAuthorizationStateProvider @Inject constructor(
    private val tokenHolder: TokenHolder,
    private val credentialsProvider: CredentialsProvider
): AuthorizationStateProvider {

    override fun isLoggedIn(): Boolean =
        tokenHolder.session == INVALID_TOKEN

    override fun isCredentialKnown(): Boolean =
        credentialsProvider.login.isNotEmpty() && credentialsProvider.password.isNotEmpty()

}