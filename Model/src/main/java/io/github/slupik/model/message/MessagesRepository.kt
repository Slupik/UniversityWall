/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.message

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
interface MessagesRepository {

    fun set(messages: List<Message>): Completable

    fun save(messages: List<Message>): Completable

    fun fetchAll(): Flowable<List<Message>>

    fun getAll(): Single<List<Message>>

    fun deleteAll()

}