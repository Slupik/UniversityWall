/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.scanner

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.ml.common.FirebaseMLException
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.CodeScanningFragmentBinding
import io.github.slupik.universitywall.device.camera.scanner.CameraSource
import io.github.slupik.universitywall.fragment.FragmentWithDataBinding
import io.github.slupik.universitywall.screen.scanner.model.qrscanning.QrScanningProcessor
import kotlinx.android.synthetic.main.code_scanning_fragment.*
import java.io.IOException
import kotlin.reflect.KClass

private const val TAG = "CodeScannerFragment"

class CodeScannerFragment :
    FragmentWithDataBinding<CodeScannerViewModel, CodeScanningFragmentBinding>() {

    private var cameraSource: CameraSource? = null

    override fun getLayoutId(): Int =
        R.layout.code_scanning_fragment

    override fun getFragmentClass(): KClass<CodeScannerViewModel> =
        CodeScannerViewModel::class

    override fun bindViewModel() {
        binding.viewmodel = internalViewModel
    }

    override fun onViewModelCreated(viewModel: CodeScannerViewModel) {
        super.onViewModelCreated(viewModel)
        appDepInComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkViewElements("onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        checkViewElements("onStart")
        createCameraSource()
    }

    public override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private fun startCameraSource() {
        cameraSource?.let {
            try {
                checkViewElements("onResume")
                binding.firePreview.start(cameraSource, binding.fireFaceOverlay)
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                cameraSource?.release()
                cameraSource = null
            }
        }
    }

    private fun checkViewElements(step: String) {
        if (firePreview == null) {
            Log.d(TAG, "$step: Preview is null")
        }
        if (fireFaceOverlay == null) {
            Log.d(TAG, "$step: graphicOverlay is null")
        }
    }

    override fun onPause() {
        super.onPause()
        stopCamera()
    }

    private fun stopCamera() {
        binding.firePreview.stop()
    }

    public override fun onDestroy() {
        super.onDestroy()
        cameraSource?.release()
    }

    private fun createCameraSource() {
        if (cameraSource == null) {
            cameraSource = CameraSource(activity, binding.fireFaceOverlay)
        }

        try {
            Log.i(TAG, "Using QR coded Detector Processor")
            cameraSource?.setMachineLearningFrameProcessor(QrScanningProcessor())
        } catch (e: FirebaseMLException) {
            Log.e(TAG, "can not create camera source: QR code")
        }
    }


    override fun getViewModel() =
        activityDepInComponent.codeScannerViewModelFactory.create()

    companion object {
        fun newInstance() = CodeScannerFragment()
    }

}
