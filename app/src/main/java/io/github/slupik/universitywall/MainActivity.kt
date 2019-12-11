package io.github.slupik.universitywall

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import io.github.slupik.model.authorization.state.AuthorizationStateProvider
import io.github.slupik.universitywall.activity.Activity
import io.github.slupik.universitywall.fragment.Fragment
import io.github.slupik.universitywall.screen.login.LoginFragment
import io.github.slupik.universitywall.screen.messages.MessagesFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class MainActivity : Activity() {

    @Inject
    lateinit var authorizationStateProvider: AuthorizationStateProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appDepInComponent.inject(this)

        if (savedInstanceState == null) {
            setupFragment()
        }

        authorizationStateProvider
            .state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                setupFragment()
            }.remember()
    }

    private fun setupFragment() {
        if(findViewById<FrameLayout>(R.id.main_container) != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, getFragment())
                .commitNow()
        }
    }

    private fun getFragment(): Fragment {
        return if (authorizationStateProvider.isLoggedIn()) {
            LoginFragment.newInstance()
        } else {
            MessagesFragment.newInstance()
        }
    }

    fun startQrActivity(view: View) {
        setupFragment()
    }

}