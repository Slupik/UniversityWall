/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.slupik.repository.message.database.MessageDao
import io.github.slupik.repository.message.database.MessagesDatabase

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
class MessageDatabaseModule {

    @Provides
    fun database(context: Context): MessagesDatabase =
        MessagesDatabase.getInstance(context)

    @Provides
    fun dao(database: MessagesDatabase): MessageDao =
        database.messageDao()

}