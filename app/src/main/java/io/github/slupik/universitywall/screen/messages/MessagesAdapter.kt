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
import io.github.slupik.model.message.MessageType
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.adapter.DataBoundListAdapter
import io.github.slupik.universitywall.databinding.MessageViewBinding
import io.github.slupik.universitywall.screen.messages.model.DisplayableMessage


/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

private const val MESSAGE_TYPE_TEST = 0
private const val MESSAGE_TYPE_CANCELED_CLASSES = 1
private const val MESSAGE_TYPE_INFO = 2

class MessagesAdapter constructor(
    private val viewModel: MessagesViewModel
): DataBoundListAdapter<DisplayableMessage>(

    diffCallback = object: DiffUtil.ItemCallback<DisplayableMessage>() {
        override fun areItemsTheSame(oldItem: DisplayableMessage, newItem: DisplayableMessage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DisplayableMessage, newItem: DisplayableMessage): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun getItemViewType(position: Int): Int =
        when(getItem(position).type) {
            MessageType.TEST -> MESSAGE_TYPE_TEST
            MessageType.CANCELED_CLASSES -> MESSAGE_TYPE_CANCELED_CLASSES
            MessageType.INFO -> MESSAGE_TYPE_INFO
        }

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.message_view,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: DisplayableMessage) {
        when (binding) {
            is MessageViewBinding -> {
                binding.messageTitle.text = item.header
                binding.messageCreatorName.text = item.author
                binding.messageCreationTime.text = item.creationTime
                binding.messageContent.text = item.content
                binding.messageGroupName.text = item.group
                if(!item.attachmentName.isBlank()) {
                    binding.messageFileInfo.visibility = View.GONE
                } else {
                    binding.messageFileInfo.text = item.attachmentName
                }
            }
        }
    }

}