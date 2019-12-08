/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.login

import android.view.View.GONE
import android.view.View.VISIBLE

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
sealed class LoginViewState(
    val loading: Int = GONE,
    val wrongLogin: Int = GONE,
    val wrongPassword: Int = GONE
)

class StartViewState(): LoginViewState()

class WrongLoginViewState():
    LoginViewState(
        wrongLogin = VISIBLE
    )

class WrongPasswordViewState():
    LoginViewState(
        wrongPassword = VISIBLE
    )

class LoadingDataViewState():
    LoginViewState(
        loading = VISIBLE
    )