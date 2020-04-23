/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.permissions

import io.github.slupik.universitywall.viewmodel.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sebastian Witasik on 22.04.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

@Singleton
class PermissionsModel @Inject constructor(): ViewModel() {

    private val requestToGrantEmitter: PublishSubject<PermissionsRequest> = PublishSubject.create()
    val requestToGrant: Observable<PermissionsRequest> = requestToGrantEmitter

    private val resultsEmitter: PublishSubject<PermissionsRequestResult> = PublishSubject.create()
    val results: Observable<PermissionsRequestResult> = resultsEmitter

    fun onResult(receivedResult: PermissionsRequestResult) {
        resultsEmitter.onNext(receivedResult)
    }

    fun onRequest(request: PermissionsRequest) {
        requestToGrantEmitter.onNext(request)
    }

}