/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.background.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import io.github.slupik.model.message.MessagesProvider
import io.github.slupik.universitywall.MainActivity
import io.github.slupik.universitywall.background.syncing.dagger.ContextModule
import io.github.slupik.universitywall.background.syncing.dagger.DaggerBackgroundSyncingComponent
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import javax.inject.Inject


/**
 * Created by Sebastian Witasik on 13.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

const val CHANNEL_ID = "DATA_SYNCING"
const val SYNC_TIME: Long = 5*60*1000

class SynchronizingService : Service() {

    @Inject
    lateinit var provider: MessagesProvider

    private var disposed: Disposable? = null

    private val timerTask: TimerTask = object : TimerTask() {
        override fun run() {
            disposed?.apply { dispose() }
            disposed = provider
                .refresh()
                .subscribeBy()
        }
    }
    private val timer: Timer = Timer()

    override fun onStart(intent: Intent?, startid: Int) {
        makeAsForegroundService()

        DaggerBackgroundSyncingComponent
            .builder()
            .contextModule(ContextModule(applicationContext))
            .build()
            .inject(this)

        timer.schedule(timerTask, 0, SYNC_TIME)
    }

    private fun makeAsForegroundService() {
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSound(null)
            .setPriority(-2)
            .setContentTitle("Data Synchronizing Service")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Data Synchronizing Channel",
                NotificationManager.IMPORTANCE_NONE
            )
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)!!
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}