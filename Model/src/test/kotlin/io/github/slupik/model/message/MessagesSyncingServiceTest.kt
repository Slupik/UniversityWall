/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.message

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Sebastian Witasik on 13.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
internal class MessagesSyncingServiceTest {

    private lateinit var sut: MessagesSyncingService

    @Mock
    private lateinit var downloader: MessagesDownloader

    @Mock
    private lateinit var repository: MessagesRepository

    @BeforeEach
    fun build() {
        MockitoAnnotations.initMocks(this)
        sut = MessagesSyncingService(
            downloader,
            repository
        )
    }

    @Test
    fun `download different messages and save into database`() {
        val downloadedMessage = Mockito.mock(Message::class.java)
        whenever(downloadedMessage.remoteId).thenReturn(3)
        val downloadResult = listOf(downloadedMessage)
        whenever(downloader.downloadMessages()).thenReturn(Single.just(downloadResult))

        val savedMessage = Mockito.mock(Message::class.java)
        whenever(savedMessage.remoteId).thenReturn(2)
        val repositoryResult = listOf(savedMessage)
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))
        whenever(repository.save(any())).thenReturn(Completable.complete())

        sut.refresh()
            .test()
            .assertComplete()
            .assertResult(
                listOf(
                    downloadedMessage
                )
            )

        Mockito.verify(downloader, Mockito.times(1)).downloadMessages()
        Mockito.verify(repository, Mockito.times(1)).save(downloadResult)
    }

    @Test
    fun `download similar messages and save into database`() {
        val downloadedMessage = Mockito.mock(Message::class.java)
        val downloadedMessage2 = Mockito.mock(Message::class.java)
        whenever(downloadedMessage.remoteId).thenReturn(2)
        whenever(downloadedMessage2.remoteId).thenReturn(3)
        val downloadResult = listOf(downloadedMessage, downloadedMessage2)
        whenever(downloader.downloadMessages()).thenReturn(Single.just(downloadResult))

        val savedMessage = Mockito.mock(Message::class.java)
        whenever(savedMessage.remoteId).thenReturn(2)
        val repositoryResult = listOf(savedMessage)
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))
        whenever(repository.save(any())).thenReturn(Completable.complete())

        sut.refresh()
            .test()
            .assertComplete()
            .assertResult(
                listOf(
                    downloadedMessage2
                )
            )

        Mockito.verify(downloader, Mockito.times(1)).downloadMessages()
        Mockito.verify(repository, Mockito.times(1)).save(downloadResult)
    }

    @Test
    fun `download empty list and save into database`() {
        val downloadResult = listOf<Message>()
        whenever(downloader.downloadMessages()).thenReturn(Single.just(downloadResult))

        val savedMessage = Mockito.mock(Message::class.java)
        whenever(savedMessage.remoteId).thenReturn(2)
        val repositoryResult = listOf(savedMessage)
        whenever(repository.getAll()).thenReturn(Single.just(repositoryResult))
        whenever(repository.save(any())).thenReturn(Completable.complete())

        sut.refresh()
            .test()
            .assertComplete()
            .assertResult(
                listOf()
            )

        Mockito.verify(downloader, Mockito.times(1)).downloadMessages()
        Mockito.verify(repository, Mockito.times(1)).save(downloadResult)
    }

    @Test
    fun checkSinglesTest() {
        val downloadedMessage = Mockito.mock(Message::class.java)
        val downloadedMessage2 = Mockito.mock(Message::class.java)
        whenever(downloadedMessage.remoteId).thenReturn(2)
        whenever(downloadedMessage2.remoteId).thenReturn(3)
        val downloadResult = listOf(downloadedMessage, downloadedMessage2)
        val savedMessage = Mockito.mock(Message::class.java)
        whenever(savedMessage.remoteId).thenReturn(2)
        val repositoryResult = listOf(savedMessage)

        val testing = checkSingles(
            Single.just(repositoryResult),
            Single.just(downloadResult)
        ).test()
        testing.assertResult(listOf(
            downloadedMessage2
        ))
    }

    fun checkSingles(repo: Single<List<Message>>, server: Single<List<Message>>): Single<List<Message>> =
        repo
            .flatMap {rList ->
                server
                    .map {sList ->
                        sList
                            .filter {sMess ->
                                rList
                                    .none {rMess ->
                                        sMess.remoteId == rMess.remoteId
                                    }
                            }
                    }
            }

}