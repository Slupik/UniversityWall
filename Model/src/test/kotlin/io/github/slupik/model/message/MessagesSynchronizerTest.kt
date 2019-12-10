/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.message

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxjava3.subscribers.TestSubscriber
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
internal class MessagesSynchronizerTest {

    private lateinit var sut: MessagesSynchronizer

    @Mock
    private lateinit var downloader: MessagesDownloader

    @Mock
    private lateinit var repository: MessagesRepository

    @BeforeEach
    fun build() {
        MockitoAnnotations.initMocks(this)
        sut = MessagesSynchronizer(
            downloader,
            repository
        )
    }

    @Test
    fun `download new messages and save into database`() {
        val downloadResult = listOf(Mockito.mock(Message::class.java))
        whenever(downloader.downloadMessages()).thenReturn(Single.just(downloadResult))
        whenever(repository.save(any())).thenReturn(Completable.complete())
        val repositoryResult = listOf(Mockito.mock(Message::class.java), downloadResult[0])
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))

        sut.refresh().test().assertComplete()

        verify(downloader, times(1)).downloadMessages()
        verify(repository, times(1)).save(downloadResult)
    }

    @Test
    fun `download new messages and emit new list`() {
        val downloadResult = listOf(Mockito.mock(Message::class.java))
        whenever(downloader.downloadMessages()).thenReturn(Single.just(downloadResult))
        whenever(repository.save(any())).thenReturn(Completable.complete())
        val repositoryResult = listOf(Mockito.mock(Message::class.java), downloadResult[0])
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))

        val subscriber = TestSubscriber<List<Message>>()
        sut.messagesEmitter.subscribe(subscriber)

        sut.refresh().test().assertComplete()

        subscriber
            .assertValueCount(1)
            .assertValue(repositoryResult)
    }

    @Test
    fun `download new messages, save into database and emit new list`() {
        val downloadResult = listOf(Mockito.mock(Message::class.java))
        whenever(downloader.downloadMessages()).thenReturn(Single.just(downloadResult))
        whenever(repository.save(any())).thenReturn(Completable.complete())
        val repositoryResult = listOf(Mockito.mock(Message::class.java), downloadResult[0])
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))

        val subscriber = TestSubscriber<List<Message>>()
        sut.messagesEmitter.subscribe(subscriber)

        sut.refresh().test().assertComplete()

        verify(downloader, times(1)).downloadMessages()
        verify(repository, times(1)).save(downloadResult)
        verify(repository, times(1)).getAll()

        subscriber
            .assertValueCount(1)
            .assertValue(repositoryResult)
    }

    @Test
    fun `download empty list, save into database and emit new list`() {
        val downloadResult: List<Message> = listOf()
        whenever(downloader.downloadMessages()).thenReturn(Single.just(downloadResult))
        whenever(repository.save(any())).thenReturn(Completable.complete())
        val repositoryResult = listOf(Mockito.mock(Message::class.java))
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))

        val subscriber = TestSubscriber<List<Message>>()
        sut.messagesEmitter.subscribe(subscriber)

        sut.refresh().test().assertComplete()

        verify(downloader, times(1)).downloadMessages()
        verify(repository, times(1)).save(downloadResult)
        verify(repository, times(1)).getAll()

        subscriber
            .assertValueCount(1)
            .assertValue(repositoryResult)
    }

}