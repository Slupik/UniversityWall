/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.message.model

import io.github.slupik.model.message.Message
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.error.ErrorCodeMapper
import io.github.slupik.network.error.UnknownConnectionException
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MessageListResponseConverter @Inject constructor(
    private val messageConverter: ResponseConverter<MessageResponse, Message>
) : ResponseConverter<MessageListResponse, List<@JvmSuppressWildcards Message>>() {

    override fun convert(response: MessageListResponse): List<Message> {
        if (response.errorCode != 0) {
            ErrorCodeMapper
                .throwErrorForCode(response.errorCode)
                ?.objectInstance
                ?.let { exception ->
                    throw exception
                } ?: throw UnknownConnectionException()
        }
        return response.list?.map(messageConverter::convert) ?: listOf()
    }

}