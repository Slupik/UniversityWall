/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.application

import android.app.Application
import io.github.slupik.universitywall.dagger.ApplicationComponent
import io.github.slupik.universitywall.dagger.DaggerApplicationComponent

/**
 * Created by Sebastian Witasik on 07.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MyApplication: Application() {

    lateinit var mainComponent: ApplicationComponent

    override fun onCreate() {
        mainComponent = DaggerApplicationComponent.create()
        super.onCreate()
    }

}