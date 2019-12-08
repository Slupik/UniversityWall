package io.github.slupik.universitywall

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.github.slupik.universitywall.screen.login.LoginFragment
import io.github.slupik.universitywall.screen.qrcode.activity.QrCodeScannerActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }

    fun startQrActivity(view: View) {
//        val intent = Intent(this, GoogleMainActivity::class.java)
        val intent = Intent(this, QrCodeScannerActivity::class.java)
        startActivity(
            intent,
            null
        )
    }

}