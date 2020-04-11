/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.group

import io.github.slupik.model.group.GroupActions
import io.github.slupik.model.group.GroupsProvider
import io.github.slupik.network.authorization.token.TokenHolder
import io.github.slupik.network.group.retrofit.GroupActionsService
import io.reactivex.Completable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 12.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class ServerAwareGroupActions @Inject constructor(
    private val tokenHolder: TokenHolder,
    private val service: GroupActionsService,
    private val groupsProvider: GroupsProvider
) : GroupActions {

    override fun join(id: String): Completable =
        service
            .joinToGroup(tokenHolder.session, id)
            .flatMapCompletable {
                if (it.statusCode == 0) {
                    Completable.complete()
                } else {
                    Completable.error(GroupJoinException(it.statusCode))
                }
            }

    override fun leave(id: String): Completable =
        service
            .leaveGroup(tokenHolder.session, id)
            .flatMapCompletable {
                if (it.statusCode == 0) {
                    Completable.complete()
                } else {
                    Completable.error(GroupLeaveException(it.statusCode))
                }
            }
            .doOnComplete{
                groupsProvider
                    .refresh()
                    .subscribeOn(Schedulers.io())
                    .subscribeBy(
                        onError = {
                            it.printStackTrace()
                        }
                    )
            }

}