/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.message.model

import io.github.slupik.model.message.Message
import io.github.slupik.network.ResponseConverter
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MessageResponseConverter @Inject constructor() :
    ResponseConverter<MessageResponseConverter, Message>() {

    override fun convert(response: MessageResponseConverter): Message {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}