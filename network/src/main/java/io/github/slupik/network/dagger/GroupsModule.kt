/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.group.Group
import io.github.slupik.model.group.GroupActions
import io.github.slupik.model.group.GroupsDownloader
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.group.ServerAwareGroupActions
import io.github.slupik.network.group.ServerAwareGroupsDownloader
import io.github.slupik.network.group.model.GroupListResponse
import io.github.slupik.network.group.model.GroupListResponseConverter
import io.github.slupik.network.group.model.GroupResponse
import io.github.slupik.network.group.model.GroupResponseConverter

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class GroupsModule {

    @Binds
    abstract fun downloader(downloader: ServerAwareGroupsDownloader):
            GroupsDownloader

    @Binds
    abstract fun groupListResponseConverter(converter: GroupListResponseConverter):
            ResponseConverter<GroupListResponse, List<Group>>

    @Binds
    abstract fun groupResponseConverter(converter: GroupResponseConverter):
            ResponseConverter<GroupResponse, Group>

    @Binds
    abstract fun provideGroupActions(converter: ServerAwareGroupActions):
            GroupActions

}