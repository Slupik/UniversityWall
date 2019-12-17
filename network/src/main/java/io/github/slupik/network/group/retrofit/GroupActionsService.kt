/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.group.retrofit

import io.github.slupik.network.group.model.GroupJoinResponse
import io.github.slupik.network.group.model.GroupLeaveResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
interface GroupActionsService {

    @Headers(
        "User-Agent: UniversityWall"
    )
    @GET("group/join/{id}")
    fun joinToGroup(@Header("Authorization") token: String, @Path("id") id: String): Single<GroupJoinResponse>

    @Headers(
        "User-Agent: UniversityWall"
    )
    @GET("group/leave/{id}")
    fun leaveGroup(@Header("Authorization") token: String, @Path("id") id: String): Single<GroupLeaveResponse>

}