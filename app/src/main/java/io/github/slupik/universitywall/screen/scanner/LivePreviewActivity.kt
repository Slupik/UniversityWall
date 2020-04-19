/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */
package io.github.slupik.universitywall.screen.scanner

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import com.google.android.gms.common.annotation.KeepName
import com.google.firebase.ml.common.FirebaseMLException
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.device.camera.scanner.CameraSource
import io.github.slupik.universitywall.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_live_preview.*
import java.io.IOException
import java.util.*
import io.github.slupik.universitywall.screen.scanner.qrscanning.QrScanningProcessor as QrScanningProcessor1


/** Demo app showing the various features of ML Kit for Firebase. This class is used to
 * set up continuous frame processing on frames from a camera source.  */

private const val TAG = "LivePreviewActivity"
private const val PERMISSION_REQUESTS = 594

@KeepName
class LivePreviewActivity : AppCompatActivity(), OnRequestPermissionsResultCallback {

    private var cameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_preview)

        if (firePreview == null) {
            Log.d(TAG, "Preview is null")
        }
        if (fireFaceOverlay == null) {
            Log.d(TAG, "graphicOverlay is null")
        }

        if (allPermissionsGranted()) {
            createCameraSource()
        } else {
            getRuntimePermissions()
        }
    }

    private fun createCameraSource() {
        // If there's no existing cameraSource, create one.
        if (cameraSource == null) {
            cameraSource = CameraSource(this, fireFaceOverlay)
        }

        try {
            Log.i(TAG, "Using QR coded Detector Processor")
            cameraSource?.setMachineLearningFrameProcessor(QrScanningProcessor1())
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
                if (firePreview == null) {
                    Log.d(TAG, "resume: Preview is null")
                }
                if (fireFaceOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null")
                }
                firePreview?.start(cameraSource, fireFaceOverlay)
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                cameraSource?.release()
                cameraSource = null
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    override fun onPause() {
        super.onPause()
        stopCamera()
    }

    private fun stopCamera() {
        firePreview?.stop()
    }

    public override fun onDestroy() {
        super.onDestroy()
        cameraSource?.release()
    }

    private fun getRuntimePermissions() {
        val requiredPermissions = PermissionUtils.getAllAppNeededPermissions(this)
        val neededPermissions = PermissionUtils.getNotGrantedPermissions(this, requiredPermissions)
        requestForPermissionsGrant(neededPermissions)
    }

    private fun requestForPermissionsGrant(neededPermissions: ArrayList<String>) {
        if (neededPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this, neededPermissions.toTypedArray(), PERMISSION_REQUESTS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (allPermissionsGranted()) {
            createCameraSource()
        } else if (requestCode == PERMISSION_REQUESTS) {
            askUserForPermissions()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun askUserForPermissions() {
        AlertDialog.Builder(this)
            .setTitle(R.string.required_permissions_dialog_title)
            .setMessage(R.string.required_permissions_dialog_message)
            .setPositiveButton(android.R.string.yes) { _, _ ->
                getRuntimePermissions()
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(R.drawable.dialog_error_icon)
            .show()
    }

    private fun allPermissionsGranted(): Boolean =
        PermissionUtils.allPermissionsGranted(this)

}
