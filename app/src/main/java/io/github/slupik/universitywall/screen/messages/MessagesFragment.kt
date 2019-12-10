/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.slupik.model.Converter
import io.github.slupik.model.message.Message
import io.github.slupik.model.message.MessageType
import io.github.slupik.model.message.MessagesProvider
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.MessagesFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithViewModel
import io.github.slupik.universitywall.screen.messages.model.DisplayableMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.reflect.KClass

class MessagesFragment : FragmentWithViewModel<MessagesViewModel>() {

    private lateinit var binding: MessagesFragmentBinding
    private lateinit var adapter: MessagesAdapter

    @Inject
    lateinit var messagesProvider: MessagesProvider

    @Inject
    lateinit var messagesConverter: Converter<Message, DisplayableMessage>

    companion object {
        fun newInstance() = MessagesFragment()
    }

    override fun getLayoutId(): Int =
        R.layout.messages_fragment

    override fun getFragmentClass(): KClass<MessagesViewModel> =
        MessagesViewModel::class

    override fun bindModelToView() {
        binding = DataBindingUtil.setContentView(activity!!, getLayoutId())
        binding.viewmodel = internalViewModel
        binding.setLifecycleOwner {
            viewLifecycleOwner.lifecycle
        }
    }

    override fun onViewModelCreated(viewModel: MessagesViewModel) {
        super.onViewModelCreated(viewModel)
        dependencyInjectionComponent.inject(this)

        adapter = MessagesAdapter(
            viewModel = viewModel
        )

        binding.rvMessages.adapter = adapter
        binding.rvMessages.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        messagesProvider.messagesEmitter.subscribe {
            adapter.submitList(
                it.map(messagesConverter::convert)
            )
        }.remember()
        messagesProvider
            .messages
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    adapter.submitList(
                        it.map(messagesConverter::convert)
                    )
                },
                {
                    it.printStackTrace()
                }
            ).remember()
        adapter.submitList(
            mutableListOf(
                DisplayableMessage(
                    id = 0,
                    type = MessageType.INFO,
                    header = "Lorem Ipsum",
                    content = "Maecenas feugiat eros pellentesque, molestie nisi ut, semper nulla. Etiam condimentum augue ac tincidunt sollicitudin.",
                    group = "Programming",
                    author = "Name Surname",
                    creationTime = Calendar.getInstance().time.toString(),
                    eventTime = Calendar.getInstance().time.toString(),
                    attachmentName = "file name",
                    attachmentUrl = "https://url.to.file.com/path/to/file"
                )
            )
        )

        binding.btnRefreshMessages.setOnClickListener {
            messagesProvider.refresh()
        }
    }

}
