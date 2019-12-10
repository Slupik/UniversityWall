/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages.model

import io.github.slupik.model.Converter
import io.github.slupik.model.message.Message
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MessageConverter @Inject constructor() : Converter<Message, DisplayableMessage>() {

    override fun convert(input: Message): DisplayableMessage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}