package io.github.slupik.universitywall.screen.registration

import androidx.lifecycle.MutableLiveData
import com.squareup.inject.assisted.AssistedInject
import io.github.slupik.model.authorization.credentials.CredentialsValidator
import io.github.slupik.model.authorization.registration.Registrant
import io.github.slupik.model.authorization.registration.RegistrationResult
import io.github.slupik.model.utils.subscribeOnIOThread
import io.github.slupik.universitywall.utils.observeOnMainThread
import io.github.slupik.universitywall.viewmodel.ViewModel
import io.reactivex.rxkotlin.subscribeBy

class RegistrationViewModel @AssistedInject constructor(
    private val registrant: Registrant,
    private val validator: CredentialsValidator
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
        if (parametersNotValid()) {
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
            .doFinally { viewState.postValue(StartViewState()) }
            .subscribeBy(
                onSuccess = { handleRegistrationResult(it) },
                onError = { errorType.postValue(ErrorType.CONNECTION_ERROR) }
            ).remember()
    }

    private fun handleRegistrationResult(result: RegistrationResult) {
        when (result) {
            RegistrationResult.CONNECTION_ERROR ->
                errorType.postValue(ErrorType.CONNECTION_ERROR)
            RegistrationResult.INVALID_LOGIN ->
                errorType.postValue(ErrorType.LOGIN_ALREADY_EXISTS)
            RegistrationResult.SUCCESS ->
                navigation.postValue(NavigationCommand.MESSAGES_SCREEN)
        }
    }

    private fun parametersNotValid(): Boolean {
        when {
            isAtLeastOneFieldEmpty() ->
                errorType.postValue(ErrorType.EMPTY_FIELD)
            validator.isValidLogin(login.value).not() ->
                errorType.postValue(ErrorType.LOGIN_TOO_SHORT)
            validator.isValidDisplayName(displayName.value).not() ->
                errorType.postValue(ErrorType.DISPLAY_NAME_TOO_SHORT)
            validator.isValidPassword(password.value).not() ->
                errorType.postValue(ErrorType.PASSWORD_NOT_MEETS_CRITERIA)
            password.value != repeatedPassword.value ->
                errorType.postValue(ErrorType.DIFFERENT_PASSWORDS)
            else -> return false
        }
        return true
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
