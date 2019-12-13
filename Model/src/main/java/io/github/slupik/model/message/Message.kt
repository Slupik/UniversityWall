/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.message

import org.threeten.bp.OffsetDateTime

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
data class Message(
    val localId: Int?,
    val remoteId: Int,
    val type: MessageType,
    val title: String,
    val content: String,
    val author: String,
    val group: String,
    val postedTime: OffsetDateTime,
    val expirationTime: OffsetDateTime,
    val beginningTime: OffsetDateTime,
    val endingTime: OffsetDateTime,
    val attachmentName: String,
    val attachmentUrl: String
)