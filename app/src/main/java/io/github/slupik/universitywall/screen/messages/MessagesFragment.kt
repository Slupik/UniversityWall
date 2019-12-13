/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages

import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.slupik.model.Converter
import io.github.slupik.model.message.Message
import io.github.slupik.model.message.MessagesProvider
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.MessagesFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithViewModel
import io.github.slupik.universitywall.screen.messages.model.DisplayableMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.reflect.KClass

class MessagesFragment : FragmentWithViewModel<MessagesViewModel>(), GraphController {

    private lateinit var binding: MessagesFragmentBinding
    private lateinit var adapter: MessagesAdapter

    @Inject
    lateinit var messagesProvider: MessagesProvider

    @Inject
    lateinit var messagesConverter: Converter<Message, DisplayableMessage>

    @Inject
    lateinit var viewLogic: MessagesViewLogic

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
        appDepInComponent.inject(this)

        internalViewModel.inject(viewLogic)
        viewLogic.inject(this)

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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
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

        binding.btnRefreshMessages.setOnClickListener {
            messagesProvider
                .refresh()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe()
                .remember()
        }
    }

    override fun moveToGroupsScreen() {
        findNavController().navigate(R.id.action_messagesFragment_to_groupsFragment)
    }

}
