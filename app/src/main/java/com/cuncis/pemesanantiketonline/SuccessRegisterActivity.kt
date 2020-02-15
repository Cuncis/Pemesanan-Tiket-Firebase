package com.cuncis.pemesanantiketonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_success_register.*

class SuccessRegisterActivity : AppCompatActivity() {

    private lateinit var appSplash: Animation
    private lateinit var btt: Animation
    private lateinit var ttb: Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_register)

        appSplash = AnimationUtils.loadAnimation(this, R.anim.app_splash)
        btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)

        btn_explore.startAnimation(btt)
        icon_success.startAnimation(appSplash)
        app_title.startAnimation(ttb)
        app_subtitle.startAnimation(ttb)

        btn_explore.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
