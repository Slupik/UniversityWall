/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization.token

import android.util.Log
import io.github.slupik.model.authorization.state.AuthorizationState
import io.github.slupik.model.authorization.state.AuthorizationStatePublisher
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 03.04.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class ServerAuthorizationResponseHandler @Inject constructor(
    private val tokenHolder: TokenHolder,
    private val statePublisher: AuthorizationStatePublisher
) : AuthorizationResponseHandler {

    override fun handleToken(token: String?) {
        if (token != null && token.isNotEmpty()) {
            tokenHolder.session = TOKEN_PREFIX + token
            statePublisher.onNewState(AuthorizationState.LOGGED_IN)
        }
    }

    override fun handleError(exception: Throwable) {
        exception.printError()
        statePublisher.onNewState(AuthorizationState.LOGGED_OUT)
    }

}

private fun Throwable.printError() {
    if (this is SocketTimeoutException) {
        Log.e("Authorization", "SocketTimeoutException: $message")
    } else {
        printStackTrace()
    }
}
