/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.registration

import androidx.fragment.app.FragmentActivity

/**
 * Created by Sebastian Witasik on 11.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
interface RegistrationErrorHandler {

    fun onError(type: ErrorType)

    fun onConnectionError()
    fun onLoginAlreadyExists()
    fun onInappropriatePassword()
    fun onDifferentPasswords()
    fun onEmptyFields(activity: FragmentActivity)

}