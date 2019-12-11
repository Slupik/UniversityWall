/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.dagger

import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides
import io.github.slupik.universitywall.activity.Activity

/**
 * Created by Sebastian Witasik on 11.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
class ActivityModule(
    private val activity: Activity
) {

    @Provides
    fun provideActivity(): Activity =
        activity

    @Provides
    fun provideFragmentActivity(): FragmentActivity =
        activity

}