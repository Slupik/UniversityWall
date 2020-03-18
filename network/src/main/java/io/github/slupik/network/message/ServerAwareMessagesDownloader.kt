/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.message

import io.github.slupik.model.message.Message
import io.github.slupik.model.message.MessagesDownloader
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.authorization.token.TokenHolder
import io.github.slupik.network.message.model.MessageListResponse
import io.github.slupik.network.message.retrofit.MessagesDownloadingService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class ServerAwareMessagesDownloader @Inject constructor(
    private val tokenHolder: TokenHolder,
    private val service: MessagesDownloadingService,
    private val converter: ResponseConverter<MessageListResponse, List<Message>>
) : MessagesDownloader {

    override fun downloadMessages(): Single<List<Message>> =
        service
            .getMessages(tokenHolder.session)
            .map(converter::convert)

}