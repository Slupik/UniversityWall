package io.github.slupik.universitywall.screen.registration

import androidx.lifecycle.MutableLiveData
import com.squareup.inject.assisted.AssistedInject
import io.github.slupik.model.authorization.registration.Registrant
import io.github.slupik.model.authorization.registration.RegistrationResult
import io.github.slupik.model.utils.subscribeOnIOThread
import io.github.slupik.universitywall.utils.observeOnMainThread
import io.github.slupik.universitywall.viewmodel.ViewModel
import io.reactivex.rxkotlin.subscribeBy

class RegistrationViewModel @AssistedInject constructor(
    private val registrant: Registrant
) : ViewModel() {

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
    val navigation: MutableLiveData<NavigationCommand> by lazy {
        MutableLiveData<NavigationCommand>()
    }
    val errorType: MutableLiveData<ErrorType> by lazy {
        MutableLiveData<ErrorType>()
    }

    init {
        viewState.postValue(StartViewState())
    }

    fun onLogin() {
        navigation.postValue(NavigationCommand.LOGIN_SCREEN)
    }

    fun onRegister() {
        if (isAtLeastOneFieldEmpty()) {
            errorType.postValue(ErrorType.EMPTY_FIELD)
            return
        }
        if (password.value != repeatedPassword.value) {
            errorType.postValue(ErrorType.DIFFERENT_PASSWORDS)
            return
        }

        viewState.postValue(LoadingDataViewState())

        registrant.register(
            login.value,
            password.value,
            displayName.value
        )
            .subscribeOnIOThread()
            .observeOnMainThread()
            .subscribeBy(
                onSuccess = {
                    when (it) {
                        RegistrationResult.CONNECTION_ERROR ->
                            errorType.postValue(ErrorType.CONNECTION_ERROR)
                        RegistrationResult.INVALID_LOGIN ->
                            errorType.postValue(ErrorType.LOGIN_ALREADY_EXISTS)
                        RegistrationResult.SUCCESS ->
                            navigation.postValue(NavigationCommand.MESSAGES_SCREEN)
                    }
                    //TODO check is this necessary
//                    viewState.postValue(StartViewState())
                },
                onError = {
                    errorType.postValue(ErrorType.CONNECTION_ERROR)
                }
            ).remember()
    }

    private fun isAtLeastOneFieldEmpty(): Boolean =
        displayName.value.isNullOrBlank() ||
                login.value.isNullOrBlank() ||
                password.value.isNullOrEmpty() ||
                repeatedPassword.value.isNullOrEmpty()

    @AssistedInject.Factory
    interface Factory {
        fun create(): RegistrationViewModel
    }

}
