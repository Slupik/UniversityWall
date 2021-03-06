/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.group.GroupsProvider
import io.github.slupik.model.group.GroupsSynchronizer

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class GroupsModule {

    @Binds
    abstract fun groupsProvider(provider: GroupsSynchronizer): GroupsProvider

}