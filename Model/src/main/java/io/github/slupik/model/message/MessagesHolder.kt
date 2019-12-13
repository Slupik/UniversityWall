/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.message

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
class MessagesHolder @Inject constructor(
    private val synchronizer: MessagesSynchronizer,
    private val repository: MessagesRepository
) : MessagesProvider {

    private val messagesPublisher: PublishSubject<List<Message>> = PublishSubject.create()
    override val messagesEmitter: Flowable<List<Message>>
        get() = messagesPublisher.toFlowable(BackpressureStrategy.BUFFER)

    override val messages: Single<List<Message>>
        get() = repository.getAll()

    override fun refresh(): Completable =
        synchronizer
            .refresh()
            .ignoreElement()
            .andThen(Completable.defer{
                repository
                    .getAll()
                    .doOnSuccess {
                        messagesPublisher
                            .onNext(it)
                    }
                    .ignoreElement()
            })

}

