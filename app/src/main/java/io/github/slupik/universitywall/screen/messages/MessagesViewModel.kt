/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages

import androidx.lifecycle.MutableLiveData
import com.squareup.inject.assisted.AssistedInject
import io.github.slupik.universitywall.viewmodel.ViewModel

class MessagesViewModel @AssistedInject constructor() : ViewModel() {

    val viewState: MutableLiveData<MessagesViewState> by lazy {
        MutableLiveData<MessagesViewState>()
    }
    val navigationCommand: MutableLiveData<NavigationCommand> by lazy {
        MutableLiveData<NavigationCommand>()
    }

    fun onGotoGroups() {
        navigationCommand.postValue(NavigationCommand.GROUPS_SCREEN)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(): MessagesViewModel
    }

}
