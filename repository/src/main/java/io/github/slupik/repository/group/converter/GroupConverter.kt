/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.group.converter

import io.github.slupik.model.Converter
import io.github.slupik.model.group.Group
import io.github.slupik.repository.database.GroupEntity
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class GroupConverter @Inject constructor() : Converter<Group, GroupEntity>() {

    override fun convert(input: Group): GroupEntity =
        GroupEntity(
            remoteId = input.remoteId,
            name = input.name,
            owner = input.owner
        ).apply {
            localId = input.localId?: -1
        }

}