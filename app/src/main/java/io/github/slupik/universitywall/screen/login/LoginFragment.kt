/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.login

import androidx.databinding.DataBindingUtil
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.LoginFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithViewModel
import javax.inject.Inject
import kotlin.reflect.KClass

class LoginFragment : FragmentWithViewModel<LoginViewModel>() {

    @Inject
    lateinit var viewLogic: LoginViewLogic

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun getLayoutId(): Int =
        R.layout.login_fragment

    override fun getFragmentClass(): KClass<LoginViewModel> =
        LoginViewModel::class

    override fun onViewModelCreated(viewModel: LoginViewModel) {
        super.onViewModelCreated(viewModel)
        appDepInComponent.inject(this)
        viewLogic.inject(internalViewModel)
        internalViewModel.setLogic(viewLogic)
        setupView()
    }

    private fun setupView() {
        internalViewModel.viewState.postValue(StartViewState())
        internalViewModel.login.postValue("")
        internalViewModel.password.postValue("")
    }

    override fun bindModelToView() {
        val binding: LoginFragmentBinding =
            DataBindingUtil.setContentView(activity!!, getLayoutId())
        binding.viewmodel = internalViewModel
        binding.setLifecycleOwner {
            viewLifecycleOwner.lifecycle
        }
    }

}
