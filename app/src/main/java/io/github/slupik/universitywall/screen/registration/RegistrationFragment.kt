package io.github.slupik.universitywall.screen.registration

import androidx.navigation.fragment.findNavController
import io.github.slupik.universitywall.R
import io.github.slupik.universitywall.databinding.RegistrationFragmentBinding
import io.github.slupik.universitywall.fragment.FragmentWithDataBinding
import io.github.slupik.universitywall.utils.subscribe
import javax.inject.Inject
import kotlin.reflect.KClass

class RegistrationFragment : FragmentWithDataBinding<RegistrationViewModel, RegistrationFragmentBinding>() {

    @Inject
    lateinit var errorHandler: RegistrationErrorHandler

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
        viewModel.navigation.subscribe(this) { onChangeScreenCommand(it) }
        viewModel.errorType.subscribe(this) { errorHandler.onError(it) }
    }

    private fun onChangeScreenCommand(command: NavigationCommand) {
        when (command) {
            NavigationCommand.MESSAGES_SCREEN -> moveToMessagesScreen()
            NavigationCommand.LOGIN_SCREEN -> moveToLoginScreen()
        }
    }

    private fun moveToMessagesScreen() {
        findNavController().navigate(R.id.action_registrationFragment_to_messagesFragment)
    }

    private fun moveToLoginScreen() {
        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
    }

    override fun bindViewModel() {
        binding.viewmodel = internalViewModel
    }

    override fun getViewModel() =
        activityDepInComponent.registrationViewModelFactory.create()

}
