package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.data.PrefsManager
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var appSplash: Animation
    private lateinit var btt: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        appSplash = AnimationUtils.loadAnimation(this,
            R.anim.app_splash
        )
        btt = AnimationUtils.loadAnimation(this,
            R.anim.btt
        )

        app_logo.startAnimation(appSplash)
        app_subtitle.startAnimation(btt)

        if (PrefsManager.getUsername(this).toString().isEmpty()) {
            Handler().postDelayed({
                startActivity(Intent(this, GetStartedActivity::class.java))
                finish()
            }, 2000)
        } else {
            Handler().postDelayed({
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }, 2000)
        }
    }
}
