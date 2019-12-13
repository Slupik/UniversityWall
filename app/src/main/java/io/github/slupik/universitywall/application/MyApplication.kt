/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.application

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.work.*
import io.github.slupik.universitywall.background.service.SynchronizingService
import io.github.slupik.universitywall.background.syncing.MESSAGES_SYNCING_WORK_NAME
import io.github.slupik.universitywall.background.syncing.MessagesSyncingWorker
import io.github.slupik.universitywall.dagger.ApplicationComponent
import io.github.slupik.universitywall.dagger.ContextModule
import io.github.slupik.universitywall.dagger.DaggerApplicationComponent
import java.util.concurrent.TimeUnit


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
        startBackgroundMessagesSyncing()
        startSyncing()
        super.onCreate()
    }

    private fun startSyncing() {
        val serviceIntent = Intent(this, SynchronizingService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun startBackgroundMessagesSyncing() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work =
            PeriodicWorkRequest
                .Builder(
                    MessagesSyncingWorker::class.java,
                    15,
                    TimeUnit.MINUTES
                )
                .setConstraints(constraints)
                .build()

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork(
            MESSAGES_SYNCING_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )
    }

}