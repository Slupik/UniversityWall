/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.qrcode

import android.view.GestureDetector
import android.view.MotionEvent
import com.google.android.gms.vision.barcode.Barcode
import io.github.slupik.universitywall.google.BarcodeGraphic
import io.github.slupik.universitywall.google.camera.GraphicOverlay

/**
 * Created by Sebastian Witasik on 03.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class CaptureGestureListener(
    private val graphicOverlay: GraphicOverlay<BarcodeGraphic>
): GestureDetector.SimpleOnGestureListener() {

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return onTap(e.rawX, e.rawY) == null || super.onSingleTapConfirmed(e)
    }

    /**
     * onTap returns the tapped barcode result to the calling Activity.
     *
     * @param rawX - the raw position of the tap
     * @param rawY - the raw position of the tap.
     * @return true if the activity is ending.
     */
    private fun onTap(
        rawX: Float,
        rawY: Float
    ): Barcode? {
        // Find tap point in preview frame coordinates.
        val location = IntArray(2)
        graphicOverlay.getLocationOnScreen(location)
        val x: Float = (rawX - location[0]) / graphicOverlay.getWidthScaleFactor()
        val y: Float = (rawY - location[1]) / graphicOverlay.getHeightScaleFactor()
        // Find the barcode whose center is closest to the tapped point.
        var best: Barcode? = null
        var bestDistance = Float.MAX_VALUE
        for (graphic in graphicOverlay.graphics) {
            val barcode = graphic.barcode
            if (barcode.boundingBox.contains(x.toInt(), y.toInt())) { // Exact hit, no need to keep looking.
                best = barcode
                break
            }
            val dx = x - barcode.boundingBox.centerX()
            val dy = y - barcode.boundingBox.centerY()
            val distance = dx * dx + dy * dy // actually squared distance
            if (distance < bestDistance) {
                best = barcode
                bestDistance = distance
            }
        }
//        if (best != null) {
//            val data = Intent()
//            data.putExtra(BarcodeCaptureActivity.BarcodeObject, best)
//            setResult(CommonStatusCodes.SUCCESS, data)
//            finish()
//            return true
//        }
//        return false
        return best
    }

}