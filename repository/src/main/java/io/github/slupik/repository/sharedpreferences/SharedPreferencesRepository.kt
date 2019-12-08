/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
abstract class SharedPreferencesRepository constructor(
    private val context: Context
) {

    abstract val preferencesNameId: Int

    protected fun saveAsynchronously(
        savingOperations: (SharedPreferences.Editor) -> Unit
    ) {
        saveAsynchronously(getSharedPreferences(), savingOperations)
    }

    protected fun saveAsynchronously(
        sharedPref: SharedPreferences,
        savingOperations: (SharedPreferences.Editor) -> Unit
    ) {
        with(sharedPref.edit()) {
            savingOperations.invoke(this)
            apply()
        }
    }

    protected fun saveSynchronously(
        savingOperations: (SharedPreferences.Editor) -> Unit
    ) {
        saveSynchronously(getSharedPreferences(), savingOperations)
    }

    protected fun saveSynchronously(
        sharedPref: SharedPreferences,
        savingOperations: (SharedPreferences.Editor) -> Unit
    ) {
        with(sharedPref.edit()) {
            savingOperations.invoke(this)
            commit()
        }
    }

    protected fun getSharedPreferences(): SharedPreferences =
        context.getSharedPreferences(
            context.getString(preferencesNameId),
            Context.MODE_PRIVATE
        )

    protected fun SharedPreferences.Editor.putString(@StringRes id: Int, value: String) {
        this.putString(
            context.getString(id),
            value
        )
    }

    protected fun getStringFromPreferences(@StringRes id: Int, defaultValue: String): String =
        getSharedPreferences().getString(context.getString(id), defaultValue)?: defaultValue

}