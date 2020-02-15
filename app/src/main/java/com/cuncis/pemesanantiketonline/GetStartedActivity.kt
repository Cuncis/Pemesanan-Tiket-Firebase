package com.cuncis.pemesanantiketonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_get_started.*
import kotlinx.android.synthetic.main.activity_get_started.view.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class GetStartedActivity : AppCompatActivity() {

    private lateinit var ttb: Animation
    private lateinit var btt: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        // load animation
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        btt = AnimationUtils.loadAnimation(this, R.anim.btt)

        emblem_app.startAnimation(ttb)
        intro_app.startAnimation(ttb)

        btn_sign_in.startAnimation(btt)
        btn_new_account_create.startAnimation(btt)

        btn_sign_in.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

        btn_new_account_create.setOnClickListener {
            startActivity(Intent(this, RegisterOneActivity::class.java))
        }
    }
}
