/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.login

import androidx.navigation.fragment.findNavController
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.LoginFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithDataBinding
import io.github.slupik.universitywall.utils.subscribe
import kotlin.reflect.KClass

class LoginFragment : FragmentWithDataBinding<LoginViewModel, LoginFragmentBinding>() {

    override fun getLayoutId(): Int =
        R.layout.login_fragment

    override fun getFragmentClass(): KClass<LoginViewModel> =
        LoginViewModel::class

    override fun onViewModelCreated(viewModel: LoginViewModel) {
        super.onViewModelCreated(viewModel)
        appDepInComponent.inject(this)
        viewModel.navigation.subscribe(this) { onChangeScreenCommand(it) }
    }

    private fun onChangeScreenCommand(command: NavigationCommand) {
        when (command) {
            NavigationCommand.REGISTRATION_SCREEN -> moveToRegistrationScreen()
            NavigationCommand.MESSAGES_SCREEN -> moveToMessagesScreen()
        }
    }

    private fun moveToMessagesScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_messagesFragment)
    }

    private fun moveToRegistrationScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    override fun bindViewModel() {
        binding.viewmodel = internalViewModel
    }

    override fun getViewModel() =
        appDepInComponent.loginViewModelFactory.create()

}
