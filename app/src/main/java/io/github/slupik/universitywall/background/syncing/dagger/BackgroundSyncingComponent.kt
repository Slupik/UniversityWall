/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.background.syncing.dagger

import dagger.Component
import io.github.slupik.model.dagger.MessagesModule
import io.github.slupik.network.dagger.NetworkModule
import io.github.slupik.repository.dagger.RepositoryModule
import io.github.slupik.universitywall.background.syncing.service.SynchronizingService
import javax.inject.Singleton

/**
 * Created by Sebastian Witasik on 12.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        MessagesModule::class
    ]
)
@Singleton
interface BackgroundSyncingComponent {

    fun inject(clazz: SynchronizingService)

}