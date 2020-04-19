/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Created by Sebastian Witasik on 19.04.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
private val EMPTY_ARRAY = arrayOfNulls<String?>(0)

object PermissionUtils {

    fun allPermissionsGranted(context: Context): Boolean =
        getAllAppNeededPermissions(context)
            .all { permission ->
                isPermissionGranted(context, permission!!)
            }

    fun getAllAppNeededPermissions(context: Context): Array<String?> =
        try {
            val info = context.packageManager
                .getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
            val requestedPermissions = info.requestedPermissions
            if (requestedPermissions.isNullOrEmpty()) {
                EMPTY_ARRAY
            } else {
                requestedPermissions
            }
        } catch (e: Exception) {
            EMPTY_ARRAY
        }

    fun arePermissionsGranted(context: Context, requiredPermissions: Array<String?>): Boolean =
        requiredPermissions.all { permission ->
            isPermissionGranted(context, permission!!)
        }

    fun getNotGrantedPermissions(
        context: Context,
        requiredPermissions: Array<String?>
    ): ArrayList<String> =
        ArrayList(
            requiredPermissions.filter { permission ->
                !isPermissionGranted(context, permission!!)
            }.map {
                it!!
            }
        )

    fun isPermissionGranted(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

}