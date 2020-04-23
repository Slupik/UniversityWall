/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.scanner

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable

/**
 * Created by Sebastian Witasik on 23.04.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

class CameraDisplayer constructor(
    private val lifecycle: Lifecycle,
    private val permissions: CameraPermissions,
    private val callback: (CameraCommand) -> Unit
): LifecycleObserver {

    private val remember: Disposable

    init {
        remember = permissions.state.subscribe {
            if (it == CameraPermissions.State.GRANTED &&
                lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
            ) {
                callback.invoke(CameraCommand.CREATE)
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                    callback.invoke(CameraCommand.START)
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        if (permissions.allPermissionsGranted()) {
            callback.invoke(CameraCommand.CREATE)
        } else {
            permissions.getRuntimePermissions()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        callback.invoke(CameraCommand.START)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        callback.invoke(CameraCommand.STOP)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        callback.invoke(CameraCommand.RELEASE)
    }

}