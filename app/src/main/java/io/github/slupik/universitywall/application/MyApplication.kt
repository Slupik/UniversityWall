/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.application

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import io.github.slupik.universitywall.background.syncing.service.SynchronizingService
import io.github.slupik.universitywall.dagger.ApplicationComponent
import io.github.slupik.universitywall.dagger.ContextModule
import io.github.slupik.universitywall.dagger.DaggerApplicationComponent


/**
 * Created by Sebastian Witasik on 07.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MyApplication : Application() {

    lateinit var mainComponent: ApplicationComponent

    override fun onCreate() {
        mainComponent = DaggerApplicationComponent
            .builder()
            .contextModule(
                ContextModule(this)
            )
            .build()
        startSyncing()
        super.onCreate()
    }

    private fun startSyncing() {
        val serviceIntent = Intent(this, SynchronizingService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

}