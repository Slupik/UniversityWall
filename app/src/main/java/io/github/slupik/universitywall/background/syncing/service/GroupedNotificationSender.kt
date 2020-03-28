/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.background.syncing.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.slupik.model.message.Message
import io.github.slupik.model.message.NewMessage
import io.github.slupik.universitywall.R
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 13.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

private const val CHANNEL_ID = "MESSAGES_INFO"
private const val GROUP_KEY_NEW_MESSAGES = "io.github.slupik.universitywall.NEW_MESSAGES"

class GroupedNotificationSender @Inject constructor(
    private val context: Context
) : NotificationSender {

    private val id: AtomicInteger = AtomicInteger(0)
    private var groupId = getID()

    override fun notifyAboutNewMessages(messages: List<NewMessage>) {
        createNotificationChannel()
        createGroup()
        sendNotifications(messages)
    }

    private fun createGroup() {
        val groupBuilder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setGroupSummary(true)
                .setGroup(GROUP_KEY_NEW_MESSAGES)
                .setSmallIcon(R.drawable.ic_school_black_24dp)
                .setStyle(NotificationCompat.InboxStyle())
                .build()
        NotificationManagerCompat.from(context).notify(groupId, groupBuilder)
    }

    private fun sendNotifications(messages: List<NewMessage>) {
        val manager = NotificationManagerCompat.from(context)
        manager.notificationChannelGroups
        for (message in messages) {
            manager.notify(getID(), getNotification(message))
        }
    }

    private fun getNotification(message: Message) =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(message.title)
            .setContentText(message.content)
            .setSmallIcon(R.drawable.ic_school_black_24dp)
            .setGroup(GROUP_KEY_NEW_MESSAGES)
            .build()

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "New Messages",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager =
                context.getSystemService(NotificationManager::class.java)!!
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun getID(): Int =
        id.incrementAndGet()

}