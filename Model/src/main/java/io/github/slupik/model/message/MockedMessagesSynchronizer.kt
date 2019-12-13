/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.message

import io.reactivex.Single
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 13.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
private const val AMOUNT_OF_MESSAGES = 0

class MockedMessagesSynchronizer @Inject constructor(): MessagesSynchronizer {

    override fun refresh(): Single<List<NewMessage>> {
        val list = mutableListOf<Message>()
        for(i in 1..AMOUNT_OF_MESSAGES) {
            list.add(getMessage(i))
        }
        return Single.just(list)
    }

    private fun getMessage(id: Int) =
        Message(
            id = id,
            type = MessageType.TEST,
            title = "title1",
            content = "Lorem ipsum $id",
            author = "Group owner $id",
            group = "group $id",
            postedTime = OffsetDateTime.now(),
            expirationTime = OffsetDateTime.now(),
            beginningTime = OffsetDateTime.now(),
            endingTime = OffsetDateTime.now(),
            attachmentName = "File to download $id",
            attachmentUrl ="url $id of file"
        )

}