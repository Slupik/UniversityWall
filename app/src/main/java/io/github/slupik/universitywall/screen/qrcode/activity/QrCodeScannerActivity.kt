/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.qrcode.activity

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.vision.barcode.Barcode
import io.github.slupik.model.invitation.providing.InvitationEmitter
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.activity.Activity
import io.github.slupik.universitywall.screen.qrcode.SharedViewModel
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.QrCodeScannerFragment
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.element.BarcodeGraphicTracker
import javax.inject.Inject

private const val RC_HANDLE_CAMERA_PERM = 2

class QrCodeScannerActivity : Activity(), BarcodeGraphicTracker.BarcodeUpdateListener  {

    private lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var invitationEmitter: InvitationEmitter

    override fun onCreate(savedInstanceState: Bundle?) {
        dependencyInjectionComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_code_scanner_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, QrCodeScannerFragment.newInstance())
                .commitNow()
        }
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        sharedViewModel.detectorIsNotOperational.observe(this, Observer {
            Log.w("QRCode detector", "Detector dependencies are not yet available.")
            val lowstorageFilter =
                IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowstorageFilter) != null

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show()
                Log.w("QrCodeScannerActivity", getString(R.string.low_storage_error))
            }
        })

        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            sharedViewModel.cameraPermissionGranted.postValue(true)
        } else {
            requestCameraPermission()
        }
        invitationEmitter.invitations.subscribe {
            Log.d("BARCODE", it.description)
        }
    }

    private fun requestCameraPermission() {
        val permissions = arrayOf(Manifest.permission.CAMERA)
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )
        ) {
            ActivityCompat.requestPermissions(this, permissions,
                RC_HANDLE_CAMERA_PERM
            )
            return
        }
        val listener = View.OnClickListener {
            ActivityCompat.requestPermissions(
                this,
                permissions,
                RC_HANDLE_CAMERA_PERM
            )
        }
        findViewById<View>(R.id.container).setOnClickListener(listener)
        sharedViewModel.rationalCameraPermission.postValue(listener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d("QrCodeScannerActivity", "Got unexpected permission result: $requestCode")
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("QrCodeScannerActivity", "Camera permission granted - initialize the camera source")
            sharedViewModel.cameraPermissionGranted.postValue(true)
            return
        }
        sharedViewModel.cameraPermissionGranted.postValue(false)
        Log.e(
            "QrCodeScannerActivity", "Permission not granted: results len = " + grantResults.size +
                    " Result code = " + if (grantResults.isNotEmpty()) grantResults[0] else "(empty)"
        )
        val listener = DialogInterface.OnClickListener { _, _ -> finish() }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Multitracker sample")
            .setMessage(R.string.no_camera_permission)
            .setPositiveButton(R.string.ok, listener)
            .show()
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        sharedViewModel.onTouchEvent(e)
        return super.onTouchEvent(e)
    }

    override fun onBarcodeDetected(barcode: Barcode?) {}

}
