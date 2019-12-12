package io.github.slupik.universitywall.screen.registration

import androidx.lifecycle.MutableLiveData
import io.github.slupik.universitywall.viewmodel.ViewModel

class RegistrationViewModel : ViewModel() {

    val login: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val displayName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val repeatedPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val viewState: MutableLiveData<RegistrationViewState> by lazy {
        MutableLiveData<RegistrationViewState>()
    }

    private lateinit var logic: RegistrationViewLogic

    fun setLogic(viewLogic: RegistrationViewLogic) {
        this.logic = viewLogic
    }

    fun onLogin() {
        logic.onLogin()
    }

    fun onRegister() {
        logic.onRegister()
    }

}
