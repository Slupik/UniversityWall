/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.activity

import androidx.appcompat.app.AppCompatActivity
import io.github.slupik.universitywall.application.MyApplication
import io.github.slupik.universitywall.dagger.ActivityModule
import io.github.slupik.universitywall.dagger.ActivitySubcomponent
import io.github.slupik.universitywall.dagger.ApplicationComponent

/**
 * Created by Sebastian Witasik on 07.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
abstract class Activity : AppCompatActivity() {

    protected val application: MyApplication
        get() = super.getApplication() as MyApplication

    val appDepInComponent: ApplicationComponent
        get() = application.mainComponent

    val activityDepInComponent: ActivitySubcomponent by lazy {
        appDepInComponent.plus(ActivityModule(this))
    }

}