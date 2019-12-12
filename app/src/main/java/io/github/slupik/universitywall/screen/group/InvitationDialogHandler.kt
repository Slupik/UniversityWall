/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

import android.app.AlertDialog
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.activity.Activity
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 12.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class InvitationDialogHandler @Inject constructor(
    private val activity: Activity
) {

    fun onGroupJoined(name: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.group_joining_success_title)
            .setMessage(activity.getString(R.string.group_joined_successfully, name))
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    fun onGroupJoiningError() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.group_joining_fail_title)
            .setMessage(activity.getString(R.string.group_joined_failed))
            .setPositiveButton(R.string.ok, null)
            .show()
    }

}