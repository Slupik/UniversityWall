/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.qrcode

import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import io.github.slupik.universitywall.viewmodel.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * Created by Sebastian Witasik on 04.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class SharedViewModel: ViewModel() {

    val rationalCameraPermission: MutableLiveData<View.OnClickListener> by lazy {
        MutableLiveData<View.OnClickListener>()
    }
    val cameraPermissionGranted: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val detectorIsNotOperational: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val touchEventsEmitter: PublishSubject<MotionEvent?> = PublishSubject.create()
    val touchEvents: Observable<MotionEvent?> = touchEventsEmitter

    fun onTouchEvent(e: MotionEvent?) {
        touchEventsEmitter.onNext(e)
    }

}