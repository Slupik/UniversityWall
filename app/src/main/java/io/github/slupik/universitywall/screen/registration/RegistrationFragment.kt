package io.github.slupik.universitywall.screen.registration

import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.RegistrationFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithViewModel
import javax.inject.Inject
import kotlin.reflect.KClass

class RegistrationFragment : FragmentWithViewModel<RegistrationViewModel>(), GraphController {

    @Inject
    lateinit var viewLogic: RegistrationViewLogic

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    override fun getLayoutId(): Int =
        R.layout.registration_fragment

    override fun getFragmentClass(): KClass<RegistrationViewModel> =
        RegistrationViewModel::class

    override fun onViewModelCreated(viewModel: RegistrationViewModel) {
        super.onViewModelCreated(viewModel)
        activityDepInComponent.inject(this)
        viewLogic.inject(internalViewModel)
        viewLogic.inject(activity!!)
        viewLogic.inject(this)
        internalViewModel.setLogic(viewLogic)
        setupView()
    }

    private fun setupView() {
        internalViewModel.viewState.postValue(StartViewState())
        internalViewModel.login.postValue("")
        internalViewModel.displayName.postValue("")
        internalViewModel.password.postValue("")
        internalViewModel.repeatedPassword.postValue("")
    }

    override fun bindModelToView() {
        val binding: RegistrationFragmentBinding =
            DataBindingUtil.setContentView(activity!!, getLayoutId())
        binding.viewmodel = internalViewModel
        binding.setLifecycleOwner {
            viewLifecycleOwner.lifecycle
        }
    }

    override fun moveToMessagesScreen() {
        findNavController().navigate(R.id.action_registrationFragment_to_messagesFragment)
    }

    override fun moveToLoginScreen() {
        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
    }

}
