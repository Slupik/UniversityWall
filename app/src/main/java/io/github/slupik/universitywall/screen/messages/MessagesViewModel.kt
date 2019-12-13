/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages

import androidx.lifecycle.MutableLiveData
import io.github.slupik.universitywall.viewmodel.ViewModel

class MessagesViewModel : ViewModel() {

    private lateinit var logic: MessagesViewLogic

    val viewState: MutableLiveData<MessagesViewState> by lazy {
        MutableLiveData<MessagesViewState>()
    }

    fun inject(logic: MessagesViewLogic) {
        this.logic = logic
    }

    fun onGotoGroups() {
        logic.onGotoGroups()
    }

}
