/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.qrcode

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.snackbar.Snackbar
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.google.BarcodeCaptureActivity
import io.github.slupik.universitywall.google.BarcodeGraphic
import io.github.slupik.universitywall.google.BarcodeGraphicTracker
import io.github.slupik.universitywall.google.BarcodeTrackerFactory
import io.github.slupik.universitywall.google.camera.CameraSource
import io.github.slupik.universitywall.google.camera.CameraSourcePreview
import io.github.slupik.universitywall.google.camera.GraphicOverlay
import java.io.IOException

private const val RC_HANDLE_CAMERA_PERM = 2
// intent request code to handle updating play services if needed.
private const val RC_HANDLE_GMS = 9001

class QrCodeScannerActivity : AppCompatActivity(), BarcodeGraphicTracker.BarcodeUpdateListener  {

    private var mCameraSource: CameraSource? = null
    private var mPreview: CameraSourcePreview? = null
    private var mGraphicOverlay: GraphicOverlay<BarcodeGraphic>? = null


    // helper objects for detecting taps and pinches.
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var gestureDetector: GestureDetector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_code_scanner_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, QrCodeScannerFragment.newInstance())
//                .commitNow()
//        }


        mPreview = findViewById(R.id.preview)
        mGraphicOverlay = findViewById<GraphicOverlay<BarcodeGraphic>?>(R.id.graphicOverlay)


        val autoFocus = intent.getBooleanExtra(BarcodeCaptureActivity.AutoFocus, false)
        val useFlash = intent.getBooleanExtra(BarcodeCaptureActivity.UseFlash, false)


        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash)
        } else {
            requestCameraPermission()
        }


        gestureDetector = GestureDetector(this, CaptureGestureListener(mGraphicOverlay!!))
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener {
            mCameraSource!!
        })

        Snackbar.make(
            mGraphicOverlay!!,
            "Tap to capture. Pinch/Stretch to zoom",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun createCameraSource(autoFocus: Boolean, useFlash: Boolean) {
        val barcodeDetector = BarcodeDetector.Builder(applicationContext).build()
        val barcodeFactory = BarcodeTrackerFactory(mGraphicOverlay, this)
        barcodeDetector.setProcessor(
            MultiProcessor.Builder(barcodeFactory).build()
        )

        if (!barcodeDetector.isOperational) {
            val lowstorageFilter =
                IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowstorageFilter) != null

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show()
                Log.w("QrCodeScannerActivity", getString(R.string.low_storage_error))
            }
        }

        mCameraSource =
            CameraSource.Builder(applicationContext, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
//                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f)
                .setFocusMode(if (autoFocus) Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE else null)
                .setFlashMode(if (useFlash) Camera.Parameters.FLASH_MODE_TORCH else null)
                .build()
    }

    private fun requestCameraPermission() {
        val permissions = arrayOf(Manifest.permission.CAMERA)
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )
        ) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
            return
        }
        val listener = View.OnClickListener {
            ActivityCompat.requestPermissions(
                this,
                permissions,
                RC_HANDLE_CAMERA_PERM
            )
        }
        findViewById<View>(R.id.topLayout).setOnClickListener(listener)
        Snackbar.make(
            mGraphicOverlay!!,
            R.string.permission_camera_rationale,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.ok, listener)
            .show()
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
            // we have permission, so create the camerasource
            val autoFocus = intent.getBooleanExtra(BarcodeCaptureActivity.AutoFocus, false)
            val useFlash = intent.getBooleanExtra(BarcodeCaptureActivity.UseFlash, false)
            createCameraSource(autoFocus, useFlash)
            return
        }
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


    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    @Throws(SecurityException::class)
    private fun startCameraSource() { // check that the device has play services available.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
            applicationContext
        )
        if (code != ConnectionResult.SUCCESS) {
            val dlg =
                GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS)
            dlg.show()
        }
        if (mCameraSource != null) {
            try {
                mPreview!!.start(mCameraSource, mGraphicOverlay)
            } catch (e: IOException) {
                Log.e("QrCodeScannerActivity", "Unable to start camera source.", e)
                mCameraSource!!.release()
                mCameraSource = null
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mPreview != null) {
            mPreview!!.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPreview != null) {
            mPreview!!.release()
        }
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        val b = scaleGestureDetector.onTouchEvent(e)
        val c = gestureDetector.onTouchEvent(e)
        return b || c || super.onTouchEvent(e)
    }

    override fun onBarcodeDetected(barcode: Barcode?) {}

}
