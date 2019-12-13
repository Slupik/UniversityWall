/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

const val MESSAGE_TYPE_TEST = 0
const val MESSAGE_TYPE_CANCELED_CLASSES = 1
const val MESSAGE_TYPE_INFO = 2

@Entity(tableName = MESSAGES_TABLE_NAME)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "authorName")
    val author: String,
    @ColumnInfo(name = "groupName")
    val group: String,
    @ColumnInfo(name = "postedTime")
    val postedTime: OffsetDateTime,
    @ColumnInfo(name = "expirationTime")
    val expirationTime: OffsetDateTime,
    @ColumnInfo(name = "beginningTime")
    val beginningTime: OffsetDateTime,
    @ColumnInfo(name = "endingTime")
    val endingTime: OffsetDateTime,
    @ColumnInfo(name = "attachmentName")
    val attachmentName: String,
    @ColumnInfo(name = "attachmentUrl")
    val attachmentUrl: String
)