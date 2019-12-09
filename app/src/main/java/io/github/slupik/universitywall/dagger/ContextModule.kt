package io.github.slupik.universitywall.dagger

import android.content.Context
import dagger.Module
import dagger.Provides



/**
 * Created by Sebastian Witasik on 09.12.2019.
 * E-mail: Sebastian Witasik
 * All rights reserved & copyright ©
 */
@Module
class ContextModule constructor(
    private val context: Context
) {

    @Provides
    fun context(): Context =
        context.applicationContext

}