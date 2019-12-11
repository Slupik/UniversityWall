/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.adapter.DataBoundListAdapter
import io.github.slupik.universitywall.databinding.GroupViewBinding
import io.github.slupik.universitywall.screen.group.model.DisplayableGroup


/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

class GroupsAdapter constructor(
    private val viewModel: GroupsViewModel
): DataBoundListAdapter<DisplayableGroup>(

    diffCallback = object: DiffUtil.ItemCallback<DisplayableGroup>() {
        override fun areItemsTheSame(oldItem: DisplayableGroup, newItem: DisplayableGroup): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DisplayableGroup, newItem: DisplayableGroup): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.group_view,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, item: DisplayableGroup) {
        when (binding) {
            is GroupViewBinding -> {
                binding.groupName.text = item.name
                binding.groupOwner.text = item.owner
            }
        }
    }

}