/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.background.syncing.dagger

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by Sebastian Witasik on 12.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
class ContextModule(
    private val context: Context
) {

    @Provides
    fun provideContext(): Context =
        context

}