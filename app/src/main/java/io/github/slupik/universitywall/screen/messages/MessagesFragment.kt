/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.slupik.model.message.MessageType
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.MessagesFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithViewModel
import io.github.slupik.universitywall.screen.messages.model.DisplayableMessage
import java.util.*
import kotlin.reflect.KClass

class MessagesFragment : FragmentWithViewModel<MessagesViewModel>() {

    private lateinit var binding: MessagesFragmentBinding
    private lateinit var adapter: MessagesAdapter

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

        adapter = MessagesAdapter(
            viewModel = viewModel
        )

        binding.rvMessages.adapter = adapter
        binding.rvMessages.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

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
    }

}
