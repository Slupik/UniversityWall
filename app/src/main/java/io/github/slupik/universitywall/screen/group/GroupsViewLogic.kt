/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

import io.github.slupik.model.group.GroupsProvider
import io.github.slupik.model.invitation.providing.InvitationEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GroupsViewLogic @Inject constructor(
    private val groupsProvider: GroupsProvider,
    private val dialogHandler: InvitationDialogHandler,
    invitationEmitter: InvitationEmitter
) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var viewModel: GroupsViewModel
    private lateinit var navigation: GraphController

    init {
        invitationEmitter
            .acceptedInvitations
            .subscribeBy(
                onNext = {
                    dialogHandler.onGroupJoined(it.description)
                },
                onError = {
                    dialogHandler.onGroupJoiningError()
                }
            )
            .remember()
    }

    fun inject(viewModel: GroupsViewModel) {
        this.viewModel = viewModel
    }

    fun inject(navigation: GraphController) {
        this.navigation = navigation
    }

    fun refresh() {
        groupsProvider
            .refresh()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
            .remember()
    }

    fun joinToGroup() {
        navigation.moveToScanner()
    }

    private fun Disposable.remember() {
        compositeDisposable.add(this)
    }

}
