/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.slupik.model.Converter
import io.github.slupik.model.group.Group
import io.github.slupik.model.group.GroupsProvider
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.GroupsFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithViewModel
import io.github.slupik.universitywall.screen.group.model.DisplayableGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.reflect.KClass

class GroupsFragment : FragmentWithViewModel<GroupsViewModel>() {

    private lateinit var binding: GroupsFragmentBinding
    private lateinit var adapter: GroupsAdapter

    @Inject
    lateinit var groupsProvider: GroupsProvider

    @Inject
    lateinit var groupsConverter: Converter<Group, DisplayableGroup>

    companion object {
        fun newInstance() = GroupsFragment()
    }

    override fun getLayoutId(): Int =
        R.layout.groups_fragment

    override fun getFragmentClass(): KClass<GroupsViewModel> =
        GroupsViewModel::class

    override fun bindModelToView() {
        binding = DataBindingUtil.setContentView(activity!!, getLayoutId())
        binding.viewmodel = internalViewModel
        binding.setLifecycleOwner {
            viewLifecycleOwner.lifecycle
        }
    }

    override fun onViewModelCreated(viewModel: GroupsViewModel) {
        super.onViewModelCreated(viewModel)
        appDepInComponent.inject(this)

        adapter = GroupsAdapter(
            viewModel = viewModel
        )

        binding.rvMessages.adapter = adapter
        binding.rvMessages.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        groupsProvider.groupsEmitter.subscribe {
            adapter.submitList(
                it.map(groupsConverter::convert)
            )
        }.remember()
        groupsProvider
            .groups
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    adapter.submitList(
                        it.map(groupsConverter::convert)
                    )
                },
                {
                    it.printStackTrace()
                }
            ).remember()
        adapter.submitList(
            mutableListOf(
                DisplayableGroup(
                    id = 0,
                    name = "name",
                    owner = "John Doe"
                )
            )
        )

        binding.btnRefreshGroups.setOnClickListener {
            groupsProvider.refresh().subscribe().remember()
        }
    }

}