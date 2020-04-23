/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.scanner

import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import io.github.slupik.universitywall.permissions.PermissionsModel
import io.github.slupik.universitywall.utils.PermissionUtils
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 23.04.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

private const val PERMISSION_REQUESTS = 594

class CameraPermissions @Inject constructor(
    private val activity: FragmentActivity,
    model: PermissionsModel
) {

    private val stateEmitter: PublishSubject<State> = PublishSubject.create()
    val state: Observable<State> = stateEmitter

    private val remember: Disposable

    init {
        remember = model.results.subscribe {
            if (it.requestCode == PERMISSION_REQUESTS) {
                if (allPermissionsGranted()) {
                    stateEmitter.onNext(State.GRANTED)
                } else {
                    stateEmitter.onNext(State.NOT_GRANTED)
                }
            }
        }
    }

    fun getRuntimePermissions() {
        val requiredPermissions = PermissionUtils.getAllAppNeededPermissions(activity)
        val neededPermissions =
            PermissionUtils.getNotGrantedPermissions(activity, requiredPermissions)
        requestForPermissionsGrant(neededPermissions)
    }

    private fun requestForPermissionsGrant(neededPermissions: ArrayList<String>) {
        if (neededPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity, neededPermissions.toTypedArray(), PERMISSION_REQUESTS
            )
        }
    }

    fun allPermissionsGranted(): Boolean =
        PermissionUtils.allPermissionsGranted(activity)

    enum class State {
        GRANTED,
        NOT_GRANTED
    }

}