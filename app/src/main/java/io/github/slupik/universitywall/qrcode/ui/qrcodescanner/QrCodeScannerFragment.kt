/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.qrcode.ui.qrcodescanner

import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.fragment.FragmentWithViewModel
import kotlin.reflect.KClass

class QrCodeScannerFragment : FragmentWithViewModel<QrCodeScannerViewModel>() {

    companion object {
        fun newInstance() =
            QrCodeScannerFragment()
    }

    override fun getLayoutId(): Int =
        R.layout.qr_code_scanner_fragment

    override fun getFragmentClass(): KClass<QrCodeScannerViewModel> =
        QrCodeScannerViewModel::class

}
