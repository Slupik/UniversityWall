/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages.model

import io.github.slupik.model.message.MessageType

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
data class DisplayableMessage(
    val id: Int,
    val type: MessageType,
    val header: String,
    val content: String,
    val group: String,
    val author: String,
    val creationTime: String,
    val eventTime: String,
    val attachmentName: String,
    val attachmentUrl: String
)