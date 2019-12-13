/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.background.syncing

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.github.slupik.model.message.MessagesProvider
import io.github.slupik.universitywall.background.syncing.dagger.ContextModule
import io.github.slupik.universitywall.background.syncing.dagger.DaggerBackgroundSyncingComponent
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 12.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

const val MESSAGES_SYNCING_WORK_NAME = "messages-syncing"

class MessagesSyncingWorker(
    context: Context,
    params: WorkerParameters
): RxWorker(context, params) {

    @Inject
    lateinit var provider: MessagesProvider

    override fun createWork(): Single<Result> {
        DaggerBackgroundSyncingComponent
            .builder()
            .contextModule(ContextModule(applicationContext))
            .build()
            .inject(this)

        Log.w("BARCODE_T", "CREATE_WORK")

        return provider
            .refresh()
            .toSingleDefault(Result.success())
            .onErrorReturnItem(Result.failure())
    }


}