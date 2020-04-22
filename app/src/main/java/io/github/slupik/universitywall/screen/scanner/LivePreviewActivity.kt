/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */
package io.github.slupik.universitywall.screen.scanner

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import com.google.android.gms.common.annotation.KeepName
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.activity.Activity
import io.github.slupik.universitywall.utils.PermissionUtils
import java.util.*


/** Demo app showing the various features of ML Kit for Firebase. This class is used to
 * set up continuous frame processing on frames from a camera source.  */

private const val TAG = "LivePreviewActivity"
private const val PERMISSION_REQUESTS = 594

@KeepName
//TODO move permissions to another place
class LivePreviewActivity : Activity(), OnRequestPermissionsResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_preview)

        if (allPermissionsGranted()) {
            //TODO repair
//            createCameraSource()
        } else {
            getRuntimePermissions()
        }
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
//            createCameraSource()
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
