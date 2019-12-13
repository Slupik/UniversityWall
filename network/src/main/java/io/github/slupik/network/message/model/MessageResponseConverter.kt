/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.message.model

import android.util.Log
import io.github.slupik.model.message.Message
import io.github.slupik.model.message.MessageType
import io.github.slupik.network.ResponseConverter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MessageResponseConverter @Inject constructor() :
    ResponseConverter<MessageResponse, Message>() {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    override fun convert(response: MessageResponse): Message {
        Log.d("BARCODE_T", "response "+response.postedTime)
        return Message(
            id = response.id,
            type = getMessageType(response.type),
            title = response.title,
            content = response.content,
            author = response.author,
            group = response.group,
            postedTime = toOffsetDateTime(response.postedTime) ?: OffsetDateTime.now(),
            expirationTime = toOffsetDateTime(response.expirationTime) ?: OffsetDateTime.now(),
            beginningTime = toOffsetDateTime(response.beginningTime) ?: OffsetDateTime.now(),
            endingTime = toOffsetDateTime(response.endingTime) ?: OffsetDateTime.now(),
            attachmentName = response.attachmentName,
            attachmentUrl = response.attachmentUrl
        )
    }

    private fun getMessageType(typeId: Int): MessageType =
        when(typeId) {
            MESSAGE_TYPE_TEST -> MessageType.TEST
            MESSAGE_TYPE_INFO -> MessageType.INFO
            MESSAGE_TYPE_CANCELED_CLASSES -> MessageType.CANCELED_CLASSES
            else -> MessageType.INFO
        }

    private fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

}