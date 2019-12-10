/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.authorization.authorizer

import io.reactivex.Single

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
interface Authorizer {

    fun logIn(login: String, password: String): Single<AuthorizationResult>

}