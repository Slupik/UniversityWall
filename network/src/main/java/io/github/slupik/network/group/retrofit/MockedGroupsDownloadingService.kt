/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.group.retrofit

import android.util.Log
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
class MockedGroupsDownloadingService : GroupsDownloadingService {

    override fun getGroups(token: String): Single<GroupListResponse> =
        Single.just(
            GroupListResponse(
                errorCode = 0,
                list = getRandomList()
            )
        ).delay(3, TimeUnit.SECONDS)

    private fun getRandomList(): List<GroupResponse> {
        val result: MutableList<GroupResponse> = mutableListOf()
        val repetitions = Random.nextInt(0, 10)
        Log.d("Mock_Groups_Service", "Amount of downloaded groups: $repetitions")
        repeat(repetitions) {
            result.add(getGroup(Random.nextInt(0, 50)))
        }
        return result
    }

    private fun getGroup(id: Int): GroupResponse =
        GroupResponse(
            id,
            "T name $id",
            "owner $id"
        )

}