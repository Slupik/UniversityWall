/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.slupik.repository.message.converter.DateTimeConverter

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Database(entities = [MessageEntity::class, GroupEntity::class], version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun messageDao(): MainDao

    companion object {

        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context): MainDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                MainDatabase::class.java, "$DATABASE_NAME.db"
            )
                .build()
    }

}