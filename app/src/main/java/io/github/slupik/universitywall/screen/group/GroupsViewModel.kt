/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

import androidx.lifecycle.MutableLiveData
import io.github.slupik.universitywall.viewmodel.ViewModel

class GroupsViewModel : ViewModel() {

    private lateinit var logic: GroupsViewLogic

    val viewState: MutableLiveData<GroupsViewState> by lazy {
        MutableLiveData<GroupsViewState>()
    }

    fun setLogic(viewLogic: GroupsViewLogic) {
        this.logic = viewLogic
    }

    fun refresh() {
        logic.refresh()
    }

    fun joinToGroup() {
        logic.joinToGroup()
    }

}
