/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.message.retrofit

import io.github.slupik.network.message.model.MessageListResponse
import io.github.slupik.network.message.model.MessageResponse
import io.reactivex.Single
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MockedMessagesDownloadingService : MessagesDownloadingService {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    override fun getMessages(token: String): Single<MessageListResponse> =
        getListWithMessages()

    private fun getEmptyList(): Single<MessageListResponse> =
        Single.just(
            MessageListResponse(
                errorCode = 0,
                list = listOf()
            )
        ).delay(3, TimeUnit.SECONDS)

    private fun getListWithMessages(): Single<MessageListResponse> =
        Single.just(
            MessageListResponse(
                errorCode = 0,
                list = listOf(
                    getMessage(0)
                )
            )
        ).delay(3, TimeUnit.SECONDS)

    private fun getMessage(id: Int) =
        MessageResponse(
            id = id,
            type = 0,
            typeName = "testType",
            title = "title $id",
            content = "Lorem ipsum long content $id",
            author = "author $id",
            group = "group $id",
            postedTime = fromOffsetDateTime(OffsetDateTime.now()),
            expirationTime = fromOffsetDateTime(OffsetDateTime.now()),
            beginningTime = fromOffsetDateTime(OffsetDateTime.now()),
            endingTime = fromOffsetDateTime(OffsetDateTime.now()),
            attachmentName = "att name $id",
            attachmentUrl = "att url $id"
        )

    private fun fromOffsetDateTime(date: OffsetDateTime): String {
        return date.format(formatter)
    }

}