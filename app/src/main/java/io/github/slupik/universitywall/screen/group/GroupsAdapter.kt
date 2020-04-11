/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import io.github.slupik.model.group.GroupActions
import io.github.slupik.model.utils.subscribeOnIOThread
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.adapter.DataBoundListAdapter
import io.github.slupik.universitywall.databinding.GroupViewBinding
import io.github.slupik.universitywall.screen.group.model.DisplayableGroup
import io.github.slupik.universitywall.utils.observeOnMainThread
import io.reactivex.rxkotlin.subscribeBy


/**
 * Created by Sebastian Witasik on 10.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */

class GroupsAdapter constructor(
    private val actions: GroupActions
) : DataBoundListAdapter<DisplayableGroup>(

    diffCallback = object : DiffUtil.ItemCallback<DisplayableGroup>() {
        override fun areItemsTheSame(
            oldItem: DisplayableGroup,
            newItem: DisplayableGroup
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DisplayableGroup,
            newItem: DisplayableGroup
        ): Boolean {
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

                binding.btnLeaveGroup.setOnClickListener {
                    binding.btnLeaveGroup.visibility = INVISIBLE
                    binding.pbLeaving.visibility = VISIBLE
                    leaveGroup(binding, item)
                }
            }
        }
    }

    private fun leaveGroup(
        binding: GroupViewBinding,
        group: DisplayableGroup
    ) =
        actions
            .leave("" + group.id)
            .observeOnMainThread()
            .subscribeOnIOThread()
            .subscribeBy(
                onComplete = {
                    hide(binding)
                },
                onError = {
                    it.printStackTrace()
                }
            )

    private fun hide(binding: GroupViewBinding) {
        binding.btnLeaveGroup.visibility = VISIBLE
        binding.pbLeaving.visibility = View.GONE
    }

}