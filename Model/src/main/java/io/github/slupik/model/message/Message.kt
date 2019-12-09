/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.message

import java.util.*

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
data class Message(
    val type: MessageType,
    val creatorName: String,
    val groupName: String,
    val content: String,
    val postTime: Date,
    val eventTime: Date,
    val attachment: MessageAttachment
)