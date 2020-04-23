/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.scanner

import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.activity.Activity
import io.github.slupik.universitywall.databinding.CodeScanningFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithDataBinding
import kotlin.reflect.KClass

private const val TAG = "CodeScannerFragment"

class CodeScannerFragment :
    FragmentWithDataBinding<CodeScannerViewModel, CodeScanningFragmentBinding>() {

    private var displayer: CameraDisplayer? = null

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
        displayer = CameraDisplayer(
            (activity as Activity),
            {binding.firePreview},
            {binding.fireFaceOverlay}
        )
        (activity as Activity).lifecycle.addObserver(displayer!!)
    }

    override fun getViewModel() =
        activityDepInComponent.codeScannerViewModelFactory.create()

    companion object {
        fun newInstance() = CodeScannerFragment()
    }

}
