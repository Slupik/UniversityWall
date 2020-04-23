/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.scanner

import android.app.Activity
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.ml.common.FirebaseMLException
import io.github.slupik.universitywall.device.camera.scanner.CameraSource
import io.github.slupik.universitywall.device.camera.scanner.CameraSourcePreview
import io.github.slupik.universitywall.device.camera.scanner.GraphicOverlay
import io.github.slupik.universitywall.screen.scanner.model.qrscanning.QrScanningProcessor
import java.io.IOException

/**
 * Created by Sebastian Witasik on 23.04.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

private const val TAG = "CameraDisplayer"

class CameraDisplayer constructor(
    private val activity: Activity,
    private val previewProvider: ()->CameraSourcePreview,
    private val overlayProvider: ()->GraphicOverlay
): LifecycleObserver {

    private var cameraSource: CameraSource? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        createCameraSource()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        startCameraSource()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        previewProvider().stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        cameraSource?.release()
    }

    private fun createCameraSource() {
        if (cameraSource == null) {
            cameraSource = CameraSource(activity, overlayProvider())
        }

        try {
            Log.i(TAG, "Using QR coded Detector Processor")
            cameraSource?.setMachineLearningFrameProcessor(QrScanningProcessor())
        } catch (e: FirebaseMLException) {
            Log.e(TAG, "can not create camera source: QR code")
        }
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private fun startCameraSource() {
        cameraSource?.let {
            try {
                previewProvider().start(cameraSource, overlayProvider())
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                cameraSource?.release()
                cameraSource = null
            }
        }
    }

}