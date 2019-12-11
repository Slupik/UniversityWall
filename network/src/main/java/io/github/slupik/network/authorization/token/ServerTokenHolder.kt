/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization.token

import io.github.slupik.model.authorization.token.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

@Singleton
class ServerTokenHolder @Inject constructor(
    private val repository: TokenRepository
) : TokenHolder {

    override var session: String
        get() = repository.sessionToken
        set(value) = repository.saveSessionToken(value)

}