/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.message

import io.github.slupik.model.Converter
import io.github.slupik.model.message.Message
import io.github.slupik.model.message.MessagesRepository
import io.github.slupik.repository.database.MainDao
import io.github.slupik.repository.database.MessageEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MessagesDatabaseProxy @Inject constructor(
    private val database: MainDao,
    private val converterToEntity: Converter<Message, MessageEntity>,
    private val converterFromEntity: Converter<List<MessageEntity>, List<Message>>
) : MessagesRepository {

    override fun set(messages: List<Message>): Completable {
        deleteAll()
        return save(messages)
    }

    override fun save(messages: List<Message>): Completable =
        database.insertMessages(messages.map(converterToEntity::convert))

    override fun fetchAll(): Flowable<List<Message>> =
        database
            .fetchAllMessages()
            .map(converterFromEntity::convert)

    override fun getAll(): Single<List<Message>> =
        database
            .getAllMessages()
            .map(converterFromEntity::convert)

    override fun deleteAll() {
        database.deleteAllMessages()
    }

}