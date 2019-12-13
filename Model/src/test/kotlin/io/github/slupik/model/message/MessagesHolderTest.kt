/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.message

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
internal class MessagesHolderTest {

    private lateinit var sut: MessagesHolder

    @Mock
    private lateinit var synchronizer: MessagesSynchronizer

    @Mock
    private lateinit var repository: MessagesRepository

    @BeforeEach
    fun build() {
        MockitoAnnotations.initMocks(this)
        sut = MessagesHolder(
            synchronizer,
            repository
        )
    }

    @Test
    fun `refresh and get all messages from repository`() {
        val newlyDownloaded = listOf(Mockito.mock(Message::class.java))
        whenever(synchronizer.refresh()).thenReturn(Single.just(newlyDownloaded))
        whenever(repository.save(any())).thenReturn(Completable.complete())
        val repositoryResult = listOf(Mockito.mock(Message::class.java), newlyDownloaded[0])
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))

        sut.refresh().test().assertComplete()

        verify(synchronizer, times(1)).refresh()
        verify(repository, times(1)).getAll()
    }

    @Test
    fun `refresh and emit new list`() {
        val newlyDownloaded = listOf(Mockito.mock(Message::class.java))
        whenever(synchronizer.refresh()).thenReturn(Single.just(newlyDownloaded))
        whenever(repository.save(any())).thenReturn(Completable.complete())
        val repositoryResult = listOf(Mockito.mock(Message::class.java), newlyDownloaded[0])
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))

        val subscriber = TestSubscriber<List<Message>>()
        sut.messagesEmitter.subscribe(subscriber)

        sut.refresh().test().assertComplete()

        subscriber
            .assertValueCount(1)
            .assertValue(repositoryResult)
    }

    @Test
    fun `get messages from repository`() {
        val repositoryResult = listOf(Mockito.mock(Message::class.java))
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))

        sut.messages
            .test()
            .assertResult(repositoryResult)

        verify(repository, times(1)).getAll()
    }

}