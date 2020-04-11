/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

import androidx.lifecycle.MutableLiveData
import com.squareup.inject.assisted.AssistedInject
import io.github.slupik.model.Converter
import io.github.slupik.model.group.Group
import io.github.slupik.model.group.GroupActions
import io.github.slupik.model.group.GroupsProvider
import io.github.slupik.model.invitation.providing.InvitationEmitter
import io.github.slupik.model.utils.subscribeOnIOThread
import io.github.slupik.universitywall.screen.group.model.DisplayableGroup
import io.github.slupik.universitywall.utils.observeOnMainThread
import io.github.slupik.universitywall.viewmodel.ViewModel
import io.reactivex.rxkotlin.subscribeBy

class GroupsViewModel @AssistedInject constructor(
    private val groupsProvider: GroupsProvider,
    private val dialogHandler: InvitationDialogHandler,
    private val actions: GroupActions,
    private val groupsConverter: Converter<Group, DisplayableGroup>,
    invitationEmitter: InvitationEmitter
) : ViewModel() {

    val viewState: MutableLiveData<GroupsViewState> by lazy {
        MutableLiveData<GroupsViewState>()
    }
    val navigationCommand: MutableLiveData<NavigationCommand> by lazy {
        MutableLiveData<NavigationCommand>()
    }
    val groups: MutableLiveData<List<DisplayableGroup>> by lazy {
        MutableLiveData<List<DisplayableGroup>>()
    }

    init {
        invitationEmitter
            .acceptedInvitations
            .subscribeBy(
                onNext = { invitation ->
                    actions
                        .join(invitation.link)
                        .observeOnMainThread()
                        .subscribeOnIOThread()
                        .subscribeBy(
                            onComplete = {
                                dialogHandler.onGroupJoined(invitation.description)
                                refresh()
                            },
                            onError = { actionError ->
                                actionError.printStackTrace()
                                dialogHandler.onGroupJoiningError()
                            }
                        )
                },
                onError = { dialogHandler.onGroupJoiningError() }
            )
            .remember()
        updateGroups()
    }

    private fun updateGroups() {
        groupsProvider.groupsEmitter.subscribe {
            groups.postValue( it.map(groupsConverter::convert) )
        }.remember()
        groupsProvider
            .groups
            .observeOnMainThread()
            .subscribeOnIOThread()
            .subscribeBy(
                onSuccess = {
                    groups.postValue( it.map(groupsConverter::convert) )
                },
                onError = {
                    it.printStackTrace()
                }
            )
            .remember()
    }

    fun refresh() {
        viewState.postValue(LoadingDataViewState())
        groupsProvider
            .refresh()
            .observeOnMainThread()
            .subscribeOnIOThread()
            .doFinally { viewState.postValue(StartViewState())  }
            .subscribeBy(
                onError = { it.printStackTrace() }
            )
            .remember()
    }

    fun joinToGroup() {
        navigationCommand.postValue(NavigationCommand.SCANNER_VIEW)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(): GroupsViewModel
    }

}
