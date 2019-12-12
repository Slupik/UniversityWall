/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.group

import io.github.slupik.model.group.Group
import io.github.slupik.model.group.GroupsDownloader
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.authorization.token.TokenHolder
import io.github.slupik.network.group.model.GroupListResponse
import io.github.slupik.network.group.retrofit.GroupsDownloadingService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class ServerAwareGroupsDownloader @Inject constructor(
    private val tokenHolder: TokenHolder,
    private val service: GroupsDownloadingService,
    private val converter: ResponseConverter<GroupListResponse, List<Group>>
): GroupsDownloader {

    override fun downloadGroups(): Single<List<Group>> =
        service
            .getGroups(tokenHolder.session)
            .map(converter::convert)

}