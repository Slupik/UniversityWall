package io.github.slupik.universitywall

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import io.github.slupik.model.authorization.state.AuthorizationStateProvider
import io.github.slupik.universitywall.activity.Activity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class MainActivity : Activity() {

    @Inject
    lateinit var authorizationStateProvider: AuthorizationStateProvider

    private lateinit var navigator: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator = findNavController(R.id.nav_host_fragment)
        appDepInComponent.inject(this)

        if (savedInstanceState == null) {
            if (authorizationStateProvider.isLoggedIn()) {
                navigator.navigate(R.id.action_loginFragment_to_messagesFragment)
            }
        }

        authorizationStateProvider
            .state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                if (!authorizationStateProvider.isLoggedIn()) {
                    navigator.navigate(R.id.loginFragment)
                }
            }.remember()
    }

}