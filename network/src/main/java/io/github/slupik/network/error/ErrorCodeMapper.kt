/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.error

import kotlin.reflect.KClass

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
object ErrorCodeMapper {

    fun throwErrorForCode(code: Int): KClass<out ConnectionException>? =
        when(code) {
            1 -> {
                InvalidTokenException::class
            }
            else -> null
        }

}