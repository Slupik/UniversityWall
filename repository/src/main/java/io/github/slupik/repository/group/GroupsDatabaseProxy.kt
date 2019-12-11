/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.group

import io.github.slupik.model.Converter
import io.github.slupik.model.group.Group
import io.github.slupik.model.group.GroupsRepository
import io.github.slupik.repository.database.GroupEntity
import io.github.slupik.repository.database.MainDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class GroupsDatabaseProxy @Inject constructor(
    private val database: MainDao,
    private val converterToEntity: Converter<Group, GroupEntity>,
    private val converterFromEntity: Converter<List<GroupEntity>, List<Group>>
): GroupsRepository {

    override fun save(groups: List<Group>): Completable =
        database.insertGroups(groups.map(converterToEntity::convert))

    override fun fetchAll(): Flowable<List<Group>> =
        database
            .fetchAllGroups()
            .map(converterFromEntity::convert)

    override fun getAll(): Single<List<Group>> =
        database
            .getAllGroups()
            .map(converterFromEntity::convert)

    override fun deleteAll() {
        database.deleteAllMessages()
    }

}