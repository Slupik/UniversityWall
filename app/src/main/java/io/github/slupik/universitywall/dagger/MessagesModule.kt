/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.Converter
import io.github.slupik.model.message.Message
import io.github.slupik.universitywall.screen.messages.model.DisplayableMessage
import io.github.slupik.universitywall.screen.messages.model.MessageConverter

/**
 * Created by Sebastian Witasik on 05.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class MessagesModule {

    @Binds
    abstract fun invitationFactory(factory: MessageConverter):
            Converter<Message, DisplayableMessage>

}