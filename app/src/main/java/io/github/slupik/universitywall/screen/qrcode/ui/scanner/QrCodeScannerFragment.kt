/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.qrcode.ui.scanner

import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.ScaleGestureDetector
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.snackbar.Snackbar
import io.github.slupik.model.invitation.factory.InvitationFactory
import io.github.slupik.model.invitation.providing.InvitationBroadcaster
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.fragment.FragmentWithViewModel
import io.github.slupik.universitywall.screen.qrcode.SharedViewModel
import io.github.slupik.universitywall.screen.qrcode.activity.QrCodeScannerActivity
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.element.BarcodeGraphic
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.element.BarcodeTrackerFactory
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.element.camera.CameraSource
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.element.camera.CameraSourcePreview
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.element.camera.GraphicOverlay
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KClass

// intent request code to handle updating play services if needed.
private const val RC_HANDLE_GMS = 9001

class QrCodeScannerFragment : FragmentWithViewModel<QrCodeScannerViewModel>() {

    private lateinit var sharedViewModel: SharedViewModel

    private var mCameraSource: CameraSource? = null

    private var mPreview: CameraSourcePreview? = null
    private var mGraphicOverlay: GraphicOverlay<BarcodeGraphic>? = null

    // helper objects for detecting taps and pinches.
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var gestureDetector: GestureDetector

    @Inject
    lateinit var invitationBroadcaster: InvitationBroadcaster
    @Inject
    lateinit var invitationFactory: InvitationFactory

    override fun getLayoutId(): Int =
        R.layout.qr_code_scanner_fragment

    override fun getFragmentClass(): KClass<QrCodeScannerViewModel> =
        QrCodeScannerViewModel::class

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as QrCodeScannerActivity).dependencyInjectionComponent.inject(this)
        super.onActivityCreated(savedInstanceState)

        mPreview = activity?.findViewById(R.id.preview)
        mGraphicOverlay = activity?.findViewById<GraphicOverlay<BarcodeGraphic>?>(R.id.graphicOverlay)


        val captureGestureListener =
            CaptureGestureListener(
                mGraphicOverlay!!
            )
        captureGestureListener.selected.subscribe {barcode ->
            invitationFactory
                .create(barcode.rawValue)
                .doOnSuccess {invitation ->
                    invitationBroadcaster.broadcast(invitation)
                }
                .subscribe()
        }

        gestureDetector = GestureDetector(context, captureGestureListener)
        scaleGestureDetector = ScaleGestureDetector(context,
            ScaleListener {
                mCameraSource!!
            })

        Snackbar.make(
            mGraphicOverlay!!,
            "Tap to capture. Pinch/Stretch to zoom",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun createCameraSource(autoFocus: Boolean, useFlash: Boolean) {
        val barcodeDetector = BarcodeDetector.Builder(context).build()
        val barcodeFactory =
            BarcodeTrackerFactory(
                mGraphicOverlay,
                context,
                invitationFactory
            )
        barcodeDetector.setProcessor(
            MultiProcessor.Builder(barcodeFactory).build()
        )

        if (!barcodeDetector.isOperational) {
            sharedViewModel.detectorIsNotOperational.postValue(true)
        }

        mCameraSource =
            CameraSource.Builder(context, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
//                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f)
                .setFocusMode(if (autoFocus) Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE else null)
                .setFlashMode(if (useFlash) Camera.Parameters.FLASH_MODE_TORCH else null)
                .build()

        startCameraSource()
    }

    override fun onViewModelCreated(viewModel: QrCodeScannerViewModel) {
        super.onViewModelCreated(viewModel)

        sharedViewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        sharedViewModel.cameraPermissionGranted.observe(this, Observer {
            if (it) {
                createCameraSource(true, false)
            }
        })
        if (sharedViewModel.cameraPermissionGranted.value == true) {
            createCameraSource(true, false)
        }

        sharedViewModel.rationalCameraPermission.observe(this, Observer {
            Snackbar.make(
                mGraphicOverlay!!,
                R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.ok, it)
                .show()
        })

        sharedViewModel.touchEvents.subscribe { event ->
            scaleGestureDetector.onTouchEvent(event)
            gestureDetector.onTouchEvent(event)
        }
    }

    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    @Throws(SecurityException::class)
    private fun startCameraSource() { // check that the device has play services available.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
            context
        )
        if (code != ConnectionResult.SUCCESS) {
            val dlg =
                GoogleApiAvailability.getInstance().getErrorDialog(activity, code,
                    RC_HANDLE_GMS
                )
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

    companion object {
        fun newInstance() =
            QrCodeScannerFragment()
    }

}
