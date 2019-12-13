/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages.model

import io.github.slupik.model.Converter
import io.github.slupik.model.message.Message
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MessageConverter @Inject constructor() : Converter<Message, DisplayableMessage>() {

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val timeFormatter = DateTimeFormatter.ISO_TIME

    override fun convert(input: Message): DisplayableMessage =
        DisplayableMessage(
            id = input.id,
            type = input.type,
            header = input.title,
            content = input.content,
            author = input.author,
            group = input.group,
            creationTime = convertToText(input.postedTime),
            eventTime = convertToText(input.expirationTime),
            attachmentName = input.attachmentName,
            attachmentUrl = input.attachmentUrl
        )

    private fun convertToText(time: OffsetDateTime): String =
        time.format(dateFormatter) + " " + time.format(timeFormatter)

}