/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.group

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
interface GroupsRepository {

    fun set(groups: List<Group>): Completable

    fun save(groups: List<Group>): Completable

    fun fetchAll(): Flowable<List<Group>>

    fun getAll(): Single<List<Group>>

    fun deleteAll()

}