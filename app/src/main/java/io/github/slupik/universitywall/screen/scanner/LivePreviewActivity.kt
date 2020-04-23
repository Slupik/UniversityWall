/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */
package io.github.slupik.universitywall.screen.scanner

import android.os.Bundle
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import com.google.android.gms.common.annotation.KeepName
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.activity.Activity
import io.github.slupik.universitywall.permissions.PermissionsModel
import io.github.slupik.universitywall.permissions.PermissionsRequestResult
import javax.inject.Inject

@KeepName
class LivePreviewActivity : Activity(), OnRequestPermissionsResultCallback {

    @Inject
    lateinit var permissions: PermissionsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDepInComponent.inject(this)
        setContentView(R.layout.activity_live_preview)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        this.permissions.onResult(
            PermissionsRequestResult(
                requestCode,
                permissions,
                grantResults
            )
        )
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
