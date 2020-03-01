package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cuncis.pemesanantiketonline.R
import kotlinx.android.synthetic.main.activity_ticket_detail.*

class TicketDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_detail)

        btn_buy_ticket.setOnClickListener {
            startActivity(Intent(this, TicketCheckoutActivity::class.java))
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}
