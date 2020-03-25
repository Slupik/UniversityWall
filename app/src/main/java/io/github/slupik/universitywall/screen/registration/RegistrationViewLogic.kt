/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.registration

import androidx.fragment.app.FragmentActivity
import io.github.slupik.model.authorization.INVALID_LOGIN
import io.github.slupik.model.authorization.INVALID_PASSWORD
import io.github.slupik.model.authorization.registration.Registrant
import io.github.slupik.model.authorization.registration.RegistrationResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegistrationViewLogic @Inject constructor(
    private val registrant: Registrant,
    private val errorHandler: RegistrationErrorHandler
) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var activity: FragmentActivity
    private lateinit var navigation: GraphController

    fun inject(viewModel: RegistrationViewModel) {
        this.viewModel = viewModel
    }

    fun onRegister() {
        if (isAtLeastOneFieldEmpty()) {
            errorHandler.onEmptyFields(activity)
            return
        }
        if (viewModel.password.value != viewModel.repeatedPassword.value) {
            errorHandler.onDifferentPasswords()
            return
        }

        viewModel.viewState.postValue(LoadingDataViewState())

        registrant.register(
            viewModel.login.value ?: INVALID_LOGIN,
            viewModel.password.value ?: INVALID_PASSWORD,
            viewModel.displayName.value ?: ""
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    when (it) {
                        RegistrationResult.CONNECTION_ERROR -> {
                            errorHandler.onConnectionError()
                        }
                        RegistrationResult.INVALID_LOGIN -> {
                            errorHandler.onLoginAlreadyExists()
                        }
                        RegistrationResult.SUCCESS -> {
                            navigation.moveToMessagesScreen()
                        }
                    }
                    viewModel.viewState.postValue(StartViewState())
                },
                onError = {
                    errorHandler.onConnectionError()
                }
            ).remember()
    }

    private fun isAtLeastOneFieldEmpty(): Boolean =
        viewModel.displayName.value.isNullOrBlank() ||
                viewModel.login.value.isNullOrBlank() ||
                viewModel.password.value.isNullOrEmpty() ||
                viewModel.repeatedPassword.value.isNullOrEmpty() ||
                (viewModel.password.value as String).length <= 4


    fun onLogin() {
        navigation.moveToLoginScreen()
    }

    fun inject(activity: FragmentActivity) {
        this.activity = activity
    }

    fun inject(graphController: GraphController) {
        this.navigation = graphController
    }

    private fun Disposable.remember() {
        compositeDisposable.add(this)
    }

}
