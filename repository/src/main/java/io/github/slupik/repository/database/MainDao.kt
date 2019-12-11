/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Dao
interface MainDao {

    @Query("SELECT * FROM $MESSAGES_TABLE_NAME")
    fun fetchAllMessages(): Flowable<List<MessageEntity>>

    @Query("SELECT * FROM $MESSAGES_TABLE_NAME")
    fun getAllMessages(): Single<List<MessageEntity>>

    @Query("SELECT * FROM $MESSAGES_TABLE_NAME WHERE localId = :id")
    fun getMessageByLocalId(id: String): Single<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: MessageEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessages(message: List<MessageEntity>): Completable

    @Query("DELETE FROM $MESSAGES_TABLE_NAME")
    fun deleteAllMessages()

}