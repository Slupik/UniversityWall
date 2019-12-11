package io.github.slupik.universitywall

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.github.slupik.universitywall.activity.Activity
import io.github.slupik.universitywall.screen.group.GroupsFragment
import io.github.slupik.universitywall.screen.qrcode.activity.QrCodeScannerActivity


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GroupsFragment.newInstance())
                .commitNow()
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