/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.group.retrofit

import io.github.slupik.network.group.model.GroupListResponse
import io.github.slupik.network.group.model.GroupResponse
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MockedGroupsDownloadingService: GroupsDownloadingService {

    override fun getGroups(token: String): Single<GroupListResponse> =
        Single.just(
            GroupListResponse(
                errorCode = 0,
                list = listOf(
                    getGroup(Random.nextInt(0, 5)),
                    getGroup(Random.nextInt(0, 5))
                )
            )
        ).delay(3, TimeUnit.SECONDS)

    private fun getGroup(id: Int): GroupResponse =
        GroupResponse(
            id,
            "T name $id",
            "owner $id"
        )

}