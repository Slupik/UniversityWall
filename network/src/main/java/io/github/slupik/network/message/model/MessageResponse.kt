/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.message.model

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

typealias DateInIsoFormat = String

const val MESSAGE_TYPE_TEST = 0
const val MESSAGE_TYPE_CANCELED_CLASSES = 1
const val MESSAGE_TYPE_INFO = 2

data class MessageResponse(
    val id: Int,
    val type: Int,
    val typeName: String,
    val title: String,
    val content: String,
    val author: String,
    val group: String,
    val postedTime: DateInIsoFormat,
    val expirationTime: DateInIsoFormat,
    val beginningTime: DateInIsoFormat,
    val endingTime: DateInIsoFormat,
    val attachmentName: String,
    val attachmentUrl: String
)