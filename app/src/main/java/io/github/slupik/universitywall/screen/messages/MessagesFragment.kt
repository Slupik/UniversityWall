/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.MessagesFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithDataBinding
import io.github.slupik.universitywall.utils.subscribe
import kotlin.reflect.KClass

class MessagesFragment : FragmentWithDataBinding<MessagesViewModel, MessagesFragmentBinding>() {

    private lateinit var adapter: MessagesAdapter

    companion object {
        fun newInstance() = MessagesFragment()
    }

    override fun getLayoutId(): Int =
        R.layout.messages_fragment

    override fun getFragmentClass(): KClass<MessagesViewModel> =
        MessagesViewModel::class

    override fun bindViewModel() {
        binding.viewmodel = internalViewModel
    }

    override fun onViewModelCreated(viewModel: MessagesViewModel) {
        super.onViewModelCreated(viewModel)
        appDepInComponent.inject(this)

        viewModel.navigationCommand.subscribe(this) {
            if(it == NavigationCommand.GROUPS_SCREEN) moveToGroupsScreen()
        }
        viewModel.dialogCommand.subscribe(this) {
            showConnectionErrorDialog()
        }

        adapter = MessagesAdapter(
            viewModel = viewModel,
            context = application
        )

        binding.rvMessages.adapter = adapter
        binding.rvMessages.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        viewModel.messages.subscribe(this) {
            adapter.submitList(it)
        }
    }

    private fun showConnectionErrorDialog() {
        AlertDialog.Builder(activity)
            .setTitle(R.string.messages_refreshing_fail_title)
            .setMessage(getString(R.string.messages_refreshing_fail))
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.messages_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.menu_show_groups -> {
                moveToGroupsScreen()
                true
            }
            R.id.menu_refresh_messages -> {
                internalViewModel.refresh()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun moveToGroupsScreen() {
        findNavController().navigate(R.id.action_messagesFragment_to_groupsFragment)
    }

    override fun getViewModel() =
        activityDepInComponent.messagesViewModelFactory.create()

}
