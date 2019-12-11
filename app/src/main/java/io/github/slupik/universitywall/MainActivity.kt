package io.github.slupik.universitywall

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.github.slupik.network.authorization.token.TokenHolder
import io.github.slupik.universitywall.activity.Activity
import io.github.slupik.universitywall.fragment.Fragment
import io.github.slupik.universitywall.screen.login.LoginFragment
import io.github.slupik.universitywall.screen.messages.MessagesFragment
import io.github.slupik.universitywall.screen.qrcode.activity.QrCodeScannerActivity
import javax.inject.Inject


class MainActivity : Activity() {

    @Inject
    lateinit var tokenHolder: TokenHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appDepInComponent.inject(this)

        if (savedInstanceState == null) {
            setupFragment()
        }
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, getFragment())
            .commitNow()
    }

    private fun getFragment(): Fragment {
        return if(tokenHolder.session.isEmpty()) {
            LoginFragment.newInstance()
        } else {
            MessagesFragment.newInstance()
        }
    }

    fun startQrActivity(view: View) {
        val intent = Intent(this, QrCodeScannerActivity::class.java)
        startActivity(
            intent,
            null
        )
    }

}