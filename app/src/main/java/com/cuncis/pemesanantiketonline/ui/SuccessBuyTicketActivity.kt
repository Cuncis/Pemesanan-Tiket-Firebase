package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.cuncis.pemesanantiketonline.R
import kotlinx.android.synthetic.main.activity_success_buy_ticket.*

class SuccessBuyTicketActivity : AppCompatActivity() {

    private lateinit var appSplash: Animation
    private lateinit var btt: Animation
    private lateinit var ttb: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_buy_ticket)

        appSplash = AnimationUtils.loadAnimation(this,
            R.anim.app_splash
        )
        btt = AnimationUtils.loadAnimation(this,
            R.anim.btt
        )
        ttb = AnimationUtils.loadAnimation(this,
            R.anim.ttb
        )

        // run animation
        icon_success_ticket.startAnimation(appSplash)

        app_title.startAnimation(ttb)
        app_subtitle.startAnimation(ttb)

        btn_view_ticket.startAnimation(btt)
        btn_my_dashboard.startAnimation(btt)

        btn_view_ticket.setOnClickListener {
            startActivity(Intent(this, MyProfileActivity::class.java))
        }

        btn_my_dashboard.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
