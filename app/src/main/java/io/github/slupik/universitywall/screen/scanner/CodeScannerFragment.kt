/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.scanner

import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.firebase.ml.common.FirebaseMLException
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.activity.Activity
import io.github.slupik.universitywall.databinding.CodeScanningFragmentBinding
import io.github.slupik.universitywall.device.camera.scanner.CameraSource
import io.github.slupik.universitywall.fragment.FragmentWithDataBinding
import io.github.slupik.universitywall.screen.scanner.model.qrscanning.QrScanningProcessor
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.KClass

private const val TAG = "CodeScannerFragment"

class CodeScannerFragment :
    FragmentWithDataBinding<CodeScannerViewModel, CodeScanningFragmentBinding>() {

    @Inject
    lateinit var permissions: CameraPermissions
    private var displayer: CameraDisplayer? = null
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
        activityDepInComponent.inject(this)
        permissions.state.subscribe {
            if (it == CameraPermissions.State.NOT_GRANTED) {
                askUserForPermissions()
            }
        }.remember()
        initDisplayer()
    }

    private fun askUserForPermissions() {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.required_permissions_dialog_title)
            .setMessage(R.string.required_permissions_dialog_message)
            .setPositiveButton(android.R.string.yes) { _, _ ->
                permissions.getRuntimePermissions()
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(R.drawable.dialog_error_icon)
            .show()
    }

    private fun initDisplayer() {
        val callback: (CameraCommand) -> Unit = { command ->
            when (command) {
                CameraCommand.CREATE -> createCameraSource()
                CameraCommand.START -> startCameraSource()
                CameraCommand.STOP -> binding.firePreview.stop()
                CameraCommand.RELEASE -> cameraSource?.release()
            }
        }
        displayer = CameraDisplayer(
            lifecycle,
            permissions,
            callback
        )
        (activity as Activity).lifecycle.addObserver(displayer!!)
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

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private fun startCameraSource() {
        cameraSource?.let {
            try {
                binding.firePreview.start(cameraSource, binding.fireFaceOverlay)
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                cameraSource?.release()
                cameraSource = null
            }
        }
    }

    override fun getViewModel() =
        activityDepInComponent.codeScannerViewModelFactory.create()

    companion object {
        fun newInstance() = CodeScannerFragment()
    }

}
