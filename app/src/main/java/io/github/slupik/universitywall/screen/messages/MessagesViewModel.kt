/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages

import androidx.lifecycle.MutableLiveData
import com.squareup.inject.assisted.AssistedInject
import io.github.slupik.model.Converter
import io.github.slupik.model.message.Message
import io.github.slupik.model.message.MessagesProvider
import io.github.slupik.universitywall.screen.messages.model.DisplayableMessage
import io.github.slupik.universitywall.utils.makeAsIoRequest
import io.github.slupik.universitywall.viewmodel.ViewModel
import io.reactivex.rxkotlin.subscribeBy

class MessagesViewModel @AssistedInject constructor(
    private val messagesProvider: MessagesProvider,
    private val messagesConverter: Converter<Message, DisplayableMessage>
) : ViewModel() {

    val viewState: MutableLiveData<MessagesViewState> by lazy {
        MutableLiveData<MessagesViewState>()
    }
    val navigationCommand: MutableLiveData<NavigationCommand> by lazy {
        MutableLiveData<NavigationCommand>()
    }
    val dialogCommand: MutableLiveData<DialogCommand> by lazy {
        MutableLiveData<DialogCommand>()
    }
    val messages: MutableLiveData<List<DisplayableMessage>> by lazy {
        MutableLiveData<List<DisplayableMessage>>()
    }

    init {
        messagesProvider
            .messages
            .makeAsIoRequest()
            .subscribe(
                {
                    messages.postValue(
                        it.map(messagesConverter::convert)
                    )
                },
                {
                    it.printStackTrace()
                    dialogCommand.postValue(MessagesRefreshingError)
                }
            ).remember()

        messagesProvider.messagesEmitter.subscribe {
            messages.postValue(
                it.map(messagesConverter::convert)
            )
        }.remember()
    }

    fun goToGroups() {
        navigationCommand.postValue(NavigationCommand.GROUPS_SCREEN)
    }

    fun refresh() {
        viewState.postValue(LoadingDataViewState())
        messagesProvider
            .refresh()
            .makeAsIoRequest()
            .subscribeBy(
                onComplete = {
                    viewState.postValue(StartViewState())
                },
                onError = {
                    it.printStackTrace()
                    viewState.postValue(StartViewState())
                    dialogCommand.postValue(MessagesRefreshingError)
                }
            )
            .remember()
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(): MessagesViewModel
    }

}
