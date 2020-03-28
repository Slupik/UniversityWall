/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.github.slupik.universitywall.viewmodel.ViewModel

/**
 * Created by Sebastian Witasik on 02.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
abstract class FragmentWithDataBinding<ViewModelType : ViewModel, DataBinding: ViewDataBinding> :
    FragmentWithViewModel<ViewModelType>() {

    protected lateinit var binding: DataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userInterface = inflater.inflate(getLayoutId(), container, false)
        return userInterface
    }

    override fun onAssignViewModel() {
        bindModelToView()
    }

    private fun bindModelToView() {
        binding = DataBindingUtil.setContentView(activity!!, getLayoutId())
        bindViewModel()
        binding.setLifecycleOwner {
            viewLifecycleOwner.lifecycle
        }
    }

    protected abstract fun bindViewModel()

}