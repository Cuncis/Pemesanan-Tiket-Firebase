package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cuncis.pemesanantiketonline.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn_to_profile.setOnClickListener {
            startActivity(Intent(this, MyProfileActivity::class.java))
        }

        btn_ticket_pisa.setOnClickListener {
            startActivity(Intent(this, TicketDetailActivity::class.java))
        }
    }
}
