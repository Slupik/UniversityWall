/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.message.Message
import io.github.slupik.model.message.MessagesRepository
import io.github.slupik.repository.Converter
import io.github.slupik.repository.message.MessagesDatabaseProxy
import io.github.slupik.repository.message.converter.MessageConverter
import io.github.slupik.repository.message.converter.MessageEntityConverter
import io.github.slupik.repository.message.converter.MessageEntityListConverter
import io.github.slupik.repository.message.database.MessageEntity

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class MessagesModule {

    @Binds
    abstract fun messagesRepository(repository: MessagesDatabaseProxy): MessagesRepository

    @Binds
    abstract fun messageConverter(repository: MessageConverter): Converter<Message, MessageEntity>

    @Binds
    abstract fun messageEntityConverter(repository: MessageEntityConverter): Converter<MessageEntity, Message>

    @Binds
    abstract fun messageEntityListConverter(repository: MessageEntityListConverter): Converter<List<MessageEntity>, List<Message>>

}