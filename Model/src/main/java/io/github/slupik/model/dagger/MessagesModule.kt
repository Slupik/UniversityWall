/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.message.MessagesHolder
import io.github.slupik.model.message.MessagesProvider
import io.github.slupik.model.message.MessagesSynchronizer
import io.github.slupik.model.message.MessagesSyncingService

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class MessagesModule {

    @Binds
    abstract fun messagesProvider(provider: MessagesHolder): MessagesProvider

    @Binds
    abstract fun messagesSynchronizer(provider: MessagesSyncingService): MessagesSynchronizer

}