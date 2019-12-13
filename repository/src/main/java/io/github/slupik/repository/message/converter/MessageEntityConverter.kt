/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.message.converter

import io.github.slupik.model.Converter
import io.github.slupik.model.message.Message
import io.github.slupik.model.message.MessageType
import io.github.slupik.repository.database.MESSAGE_TYPE_CANCELED_CLASSES
import io.github.slupik.repository.database.MESSAGE_TYPE_INFO
import io.github.slupik.repository.database.MESSAGE_TYPE_TEST
import io.github.slupik.repository.database.MessageEntity
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MessageEntityConverter @Inject constructor() : Converter<MessageEntity, Message>() {

    override fun convert(input: MessageEntity): Message =
        Message(
            id = input.id,
            type = getMessageType(input.type),
            title = input.title,
            content = input.content,
            author = input.author,
            group = input.group,
            postedTime = DateTimeConverter.toOffsetDateTime(input.postedTime)?: OffsetDateTime.now(),
            expirationTime = DateTimeConverter.toOffsetDateTime(input.expirationTime)?: OffsetDateTime.now(),
            beginningTime = DateTimeConverter.toOffsetDateTime(input.beginningTime)?: OffsetDateTime.now(),
            endingTime = DateTimeConverter.toOffsetDateTime(input.endingTime)?: OffsetDateTime.now(),
            attachmentName = input.attachmentName,
            attachmentUrl = input.attachmentUrl
        )

    private fun getMessageType(typeId: Int): MessageType =
        when(typeId) {
            MESSAGE_TYPE_TEST -> MessageType.TEST
            MESSAGE_TYPE_INFO -> MessageType.INFO
            MESSAGE_TYPE_CANCELED_CLASSES -> MessageType.CANCELED_CLASSES
            else -> MessageType.INFO
        }


}