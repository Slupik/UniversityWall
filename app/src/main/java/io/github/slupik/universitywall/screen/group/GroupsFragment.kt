/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.slupik.model.Converter
import io.github.slupik.model.group.Group
import io.github.slupik.model.group.GroupActions
import io.github.slupik.model.group.GroupsProvider
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.GroupsFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithViewModel
import io.github.slupik.universitywall.screen.group.model.DisplayableGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.reflect.KClass

class GroupsFragment : FragmentWithViewModel<GroupsViewModel>(), GraphController {

    private lateinit var binding: GroupsFragmentBinding
    private lateinit var adapter: GroupsAdapter

    @Inject
    lateinit var groupsActions: GroupActions

    @Inject
    lateinit var groupsProvider: GroupsProvider

    @Inject
    lateinit var groupsConverter: Converter<Group, DisplayableGroup>

    @Inject
    lateinit var viewLogic: GroupsViewLogic

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
        activityDepInComponent.inject(this)
        viewLogic.inject(internalViewModel)
        viewLogic.inject(this)
        internalViewModel.setLogic(viewLogic)

        adapter = GroupsAdapter(
            actions = groupsActions,
            groupsProvider = groupsProvider
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
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
    }

    override fun moveToScanner() {
        findNavController().navigate(R.id.action_groupsFragment_to_qrCodeScannerActivity)
    }

}
