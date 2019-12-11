/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.group.model

import io.github.slupik.model.group.Group
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.error.ErrorCodeMapper
import io.github.slupik.network.error.UnknownConnectionException
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class GroupListResponseConverter @Inject constructor(
    private val groupConverter: ResponseConverter<GroupResponse, Group>
) : ResponseConverter<GroupListResponse, List<@JvmSuppressWildcards Group>>() {

    override fun convert(response: GroupListResponse): List<Group> {
        if (response.errorCode != 0) {
            ErrorCodeMapper
                .throwErrorForCode(response.errorCode)
                ?.objectInstance
                ?.let { exception ->
                    throw exception
                }?: throw UnknownConnectionException()
        }
        return response.list?.map(groupConverter::convert) ?: listOf()
    }

}