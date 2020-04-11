/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.slupik.model.group.GroupActions
import io.github.slupik.model.group.GroupsProvider
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.GroupsFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithDataBinding
import io.github.slupik.universitywall.screen.group.model.DisplayableGroup
import io.github.slupik.universitywall.utils.subscribe
import javax.inject.Inject
import kotlin.reflect.KClass

class GroupsFragment : FragmentWithDataBinding<GroupsViewModel, GroupsFragmentBinding>(), GraphController {

    private lateinit var adapter: GroupsAdapter

    @Inject
    lateinit var groupsActions: GroupActions

    @Inject
    lateinit var groupsProvider: GroupsProvider

    companion object {
        fun newInstance() = GroupsFragment()
    }

    override fun getLayoutId(): Int =
        R.layout.groups_fragment

    override fun getFragmentClass(): KClass<GroupsViewModel> =
        GroupsViewModel::class

    override fun bindViewModel() {
        binding.viewmodel = internalViewModel
    }

    override fun onViewModelCreated(viewModel: GroupsViewModel) {
        super.onViewModelCreated(viewModel)
        activityDepInComponent.inject(this)
        viewModel.viewState.postValue(StartViewState())
        initRecyclerViewWithAdapter()

        viewModel.navigationCommand.subscribe(this) {
            if(it == NavigationCommand.SCANNER_VIEW) moveToScanner()
        }
        viewModel.groups.subscribe(this) {
            val updatesList = mutableListOf<DisplayableGroup>()
            updatesList.addAll(it)
            adapter.submitList(updatesList)
        }
    }

    private fun initRecyclerViewWithAdapter() {
        adapter = GroupsAdapter(actions = groupsActions)
        binding.rvGroups.adapter = adapter
        binding.rvGroups.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.groups_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.menu_add_group -> {
                internalViewModel.joinToGroup()
                true
            }
            R.id.menu_refresh_groups -> {
                internalViewModel.refresh()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun moveToScanner() {
        findNavController().navigate(R.id.action_groupsFragment_to_qrCodeScannerActivity)
    }

    override fun getViewModel() =
        activityDepInComponent.groupsViewModelFactory.create()

}
