/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.message

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Dao
interface MessageDao {

    @Query("SELECT * FROM $MESSAGES_TABLE_NAME WHERE localId = :id")
    fun getMessageByLocalId(id: String): Flowable<MessageEntity>

    @Query("SELECT * FROM $MESSAGES_TABLE_NAME")
    fun getAllMessages(id: String): Flowable<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: MessageEntity): Completable

    @Query("DELETE FROM $MESSAGES_TABLE_NAME")
    fun deleteAllMessages()

}