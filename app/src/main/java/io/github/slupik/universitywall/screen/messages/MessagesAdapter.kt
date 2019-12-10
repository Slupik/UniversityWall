/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import io.github.slupik.model.message.Message
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.adapter.DataBoundListAdapter
import io.github.slupik.universitywall.databinding.MessageViewBinding

/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MessagesAdapter constructor(
    private val viewModel: MessagesViewModel
): DataBoundListAdapter<Message>(

    diffCallback = object: DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.message_view,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: Message) {
        when (binding) {
            is MessageViewBinding -> {
                binding.messageTitle.text = item.title
                binding.messageCreatorName.text = item.creatorName
                binding.messageCreationTime.text = item.postTime.toString()
                binding.messageContent.text = item.content
                if(!item.attachment.name.isBlank()) {
                    binding.messageFileInfo.visibility = View.GONE
                } else {
                    binding.messageFileInfo.text = item.attachment.name
                }
                binding.messageGroupName.text = item.groupName
            }
        }
    }

}