/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProviders
import io.github.slupik.universitywall.activity.Activity
import io.github.slupik.universitywall.application.MyApplication
import io.github.slupik.universitywall.dagger.ActivitySubcomponent
import io.github.slupik.universitywall.dagger.ApplicationComponent
import io.github.slupik.universitywall.viewmodel.ViewModel
import kotlin.reflect.KClass

/**
 * Created by Sebastian Witasik on 02.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
abstract class FragmentWithViewModel<ViewModelType : ViewModel> : Fragment() {

    protected lateinit var internalViewModel: ViewModelType
    protected lateinit var userInterface: View

    protected val application: MyApplication
        get() = context?.applicationContext as MyApplication

    protected val appDepInComponent: ApplicationComponent
        get() = application.mainComponent

    protected val activityDepInComponent: ActivitySubcomponent
        get() = (activity as Activity).activityDepInComponent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userInterface = inflater.inflate(getLayoutId(), container, false)
        return userInterface
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        internalViewModel = ViewModelProviders.of(this).get(getFragmentClass().java)
        bindModelToView()
        onViewModelCreated(internalViewModel)
    }

    protected open fun bindModelToView() {
//        val binding: ...Binding =
//            DataBindingUtil.setContentView(activity!!, getLayoutId())
//        binding.viewmodel = internalViewModel
//        binding.setLifecycleOwner {
//            viewLifecycleOwner.lifecycle
//        }
    }

    protected open fun onViewModelCreated(viewModel: ViewModelType) {}

    abstract fun getFragmentClass(): KClass<ViewModelType>

}