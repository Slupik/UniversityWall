/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.group

import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Singleton
class GroupsSynchronizer @Inject constructor(
    private val downloader: GroupsDownloader,
    private val repository: GroupsRepository
) : GroupsProvider {

    private val groupsPublisher: PublishSubject<List<Group>> = PublishSubject.create()
    override val groupsEmitter: Flowable<List<Group>>
        get() = groupsPublisher.toFlowable(BackpressureStrategy.BUFFER)

    override val groups: Single<List<Group>>
        get() = repository.getAll()

    override fun refresh(): Completable =
        downloader
            .downloadGroups()
            .saveAndEmitNewList()

    private fun Single<List<Group>>.saveAndEmitNewList(): Completable =
        this.flatMapCompletable { messages ->
            repository
                .set(messages)
                .andThen(
                    Completable.defer {
                        emitNewList()
                    }
                )
        }

    private fun emitNewList(): Completable =
        repository
            .getAll()
            .doOnSuccess { refreshedList ->
                groupsPublisher.onNext(refreshedList)
            }.flatMapCompletable {
                Completable.complete()
            }

}

