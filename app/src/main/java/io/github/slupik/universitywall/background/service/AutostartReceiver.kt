/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.background.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

/**
 * Created by Sebastian Witasik on 13.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class AutostartReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        startSyncing(context)
    }

    private fun startSyncing(context: Context) {
        val serviceIntent = Intent(context, SynchronizingService::class.java)
        ContextCompat.startForegroundService(context, serviceIntent)
    }

}