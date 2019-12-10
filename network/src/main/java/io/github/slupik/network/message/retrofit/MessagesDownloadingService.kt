/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.message.retrofit

import io.github.slupik.network.message.model.MessageListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
interface MessagesDownloadingService {

    @GET("messages/")
    fun getMessages(@Header("token") token: String): Single<MessageListResponse>

}