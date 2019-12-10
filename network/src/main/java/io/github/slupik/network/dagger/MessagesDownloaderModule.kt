/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.message.Message
import io.github.slupik.model.message.MessagesDownloader
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.message.ServerAwareMessagesDownloader
import io.github.slupik.network.message.model.MessageListResponse
import io.github.slupik.network.message.model.MessageListResponseConverter
import io.github.slupik.network.message.model.MessageResponse
import io.github.slupik.network.message.model.MessageResponseConverter

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class MessagesDownloaderModule {

    @Binds
    abstract fun downloader(downloader: ServerAwareMessagesDownloader):
            MessagesDownloader

    @Binds
    abstract fun messageListResponseConverter(converter: MessageListResponseConverter):
            ResponseConverter<MessageListResponse, List<Message>>

    @Binds
    abstract fun messageResponseConverter(converter: MessageResponseConverter):
            ResponseConverter<MessageResponse, Message>

}