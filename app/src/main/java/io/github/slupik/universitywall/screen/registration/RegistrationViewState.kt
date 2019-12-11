/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.registration

import android.view.View.GONE
import android.view.View.VISIBLE

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
sealed class RegistrationViewState(
    val loading: Int = GONE
)

class StartViewState : RegistrationViewState()

class LoadingDataViewState :
    RegistrationViewState(
        loading = VISIBLE
    )