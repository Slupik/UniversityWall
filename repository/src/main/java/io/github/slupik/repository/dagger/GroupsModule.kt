/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.Converter
import io.github.slupik.model.group.Group
import io.github.slupik.model.group.GroupsRepository
import io.github.slupik.repository.database.GroupEntity
import io.github.slupik.repository.group.GroupsDatabaseProxy
import io.github.slupik.repository.group.converter.GroupConverter
import io.github.slupik.repository.group.converter.GroupEntityConverter
import io.github.slupik.repository.group.converter.GroupEntityListConverter

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class GroupsModule {

    @Binds
    abstract fun groupsRepository(repository: GroupsDatabaseProxy): GroupsRepository

    @Binds
    abstract fun groupConverter(repository: GroupConverter): Converter<Group, GroupEntity>

    @Binds
    abstract fun groupEntityConverter(repository: GroupEntityConverter): Converter<GroupEntity, Group>

    @Binds
    abstract fun groupEntityListConverter(repository: GroupEntityListConverter): Converter<List<GroupEntity>, List<Group>>

}