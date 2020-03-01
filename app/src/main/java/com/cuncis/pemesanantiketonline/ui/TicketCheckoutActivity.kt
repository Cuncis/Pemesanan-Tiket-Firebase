package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cuncis.pemesanantiketonline.R
import kotlinx.android.synthetic.main.activity_ticket_checkout.*

class TicketCheckoutActivity : AppCompatActivity() {

    private var valueJumlahTiket: Int = 1
    private var myBalance: Int = 200
    private var valueTotalHarga: Int = 0
    private var valueHargaTiket: Int = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_checkout)

        textjumlahtiket.text = valueJumlahTiket.toString()
        texttotalharga.text = "US$ $valueTotalHarga"
        textmybalance.text = "US$ $myBalance"

        valueTotalHarga = valueHargaTiket * valueJumlahTiket
        texttotalharga.text = "US$ $valueTotalHarga"

        btnmines.animate().alpha(0F).setDuration(300).start()
        btnmines.isEnabled = false
        notice_uang.visibility = View.GONE

        btnplus.setOnClickListener {
            valueJumlahTiket += 1
            textjumlahtiket.text = valueJumlahTiket.toString()
            if (valueJumlahTiket > 1) {
                btnmines.animate().alpha(1F).setDuration(300).start()
                btnmines.isEnabled = true
            }
            valueTotalHarga = valueHargaTiket * valueJumlahTiket
            texttotalharga.text = "US$ $valueTotalHarga"
            if (valueTotalHarga > myBalance) {
                btn_buy_ticket.animate().translationY(250F).alpha(0F).setDuration(350).start()
                btn_buy_ticket.isEnabled = false
                textmybalance.setTextColor(Color.parseColor("#D1206B"))
                notice_uang.visibility = View.VISIBLE
            }
        }

        btnmines.setOnClickListener {
            valueJumlahTiket -= 1
            textjumlahtiket.text = valueJumlahTiket.toString()
            if (valueJumlahTiket < 2) {
                btnmines.animate().alpha(0F).setDuration(300).start()
                btnmines.isEnabled = false
            }
            valueTotalHarga = valueHargaTiket * valueJumlahTiket
            texttotalharga.text = "US$ $valueTotalHarga"
            if (valueTotalHarga <= myBalance) {
                btn_buy_ticket.animate().translationY(0F).alpha(1F).setDuration(350).start()
                btn_buy_ticket.isEnabled = true
                textmybalance.setTextColor(Color.parseColor("#203DD1"))
                notice_uang.visibility = View.GONE
            }
        }

        btn_buy_ticket.setOnClickListener {
            startActivity(Intent(this, SuccessBuyTicketActivity::class.java))
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}
