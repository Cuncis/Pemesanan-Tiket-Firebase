package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.data.PrefsManager
import com.cuncis.pemesanantiketonline.util.Constants
import com.cuncis.pemesanantiketonline.util.Utils
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_ticket_checkout.*
import java.util.*

class TicketCheckoutActivity : AppCompatActivity() {

    private lateinit var databaseWisata: DatabaseReference
    private lateinit var databaseUsers: DatabaseReference
    private lateinit var databaseMyTikets: DatabaseReference

    private var valueJumlahTiket: Int = 1
    private var myBalance: Int = 0
    private var valueTotalHarga: Int = 0
    private var valueHargaTiket: Int = 0
    private var restOfBalance: Int = 0

    private var dateWisata = ""
    private var timeWisata = ""

    private var numberOfTransaction: Int = Random().nextInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_checkout)

        val bundle = intent.extras
        val newTicket = bundle?.getString(Constants.KEY_TICKET)

        textjumlahtiket.text = valueJumlahTiket.toString()
        texttotalharga.text = "US$ $valueTotalHarga"

        btnmines.animate().alpha(0F).setDuration(300).start()
        btnmines.isEnabled = false
        notice_uang.visibility = View.GONE

        databaseUsers = FirebaseDatabase.getInstance().reference.child("Users").child(PrefsManager.getUsername(this).toString())
        databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                myBalance = dataSnapshot.child("user_balance").value.toString().toInt()
                textmybalance.text = "US$ $myBalance"
            }
            override fun onCancelled(p0: DatabaseError) {
                Utils.showLog("Users Error: " + p0.message)
            }
        })

        databaseWisata = FirebaseDatabase.getInstance().reference.child("Wisata").child(newTicket!!)
        databaseWisata.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv_nama_wisata.text = dataSnapshot.child("nama_wisata").value.toString()
                tv_lokasi.text = dataSnapshot.child("lokasi").value.toString()
                tv_ketentuan.text = dataSnapshot.child("ketentuan").value.toString()
                valueHargaTiket = dataSnapshot.child("harga_tiket").value.toString().toInt()

                dateWisata = dataSnapshot.child("date_wisata").value.toString()
                timeWisata = dataSnapshot.child("time_wisata").value.toString()

                valueTotalHarga = valueHargaTiket * valueJumlahTiket
                texttotalharga.text = "US$ $valueTotalHarga"
            }
            override fun onCancelled(p0: DatabaseError) {
                Utils.showLog("Wisata Error: " + p0.message)
            }
        })

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
            databaseMyTikets = FirebaseDatabase.getInstance().reference.child("MyTikets")
                .child(PrefsManager.getUsername(this).toString()).child(tv_nama_wisata.text.toString() + numberOfTransaction)
            databaseMyTikets.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    databaseMyTikets.ref.child("id_ticket").setValue(tv_nama_wisata.text.toString() + numberOfTransaction)
                    databaseMyTikets.ref.child("nama_wisata").setValue(tv_nama_wisata.text.toString())
                    databaseMyTikets.ref.child("lokasi").setValue(tv_lokasi.text.toString())
                    databaseMyTikets.ref.child("ketentuan").setValue(tv_ketentuan.text.toString())
                    databaseMyTikets.ref.child("jumlah_tiket").setValue(valueJumlahTiket)
                    databaseMyTikets.ref.child("date_wisata").setValue(dateWisata)
                    databaseMyTikets.ref.child("time_wisata").setValue(timeWisata)

                    startActivity(Intent(this@TicketCheckoutActivity, SuccessBuyTicketActivity::class.java))
                }
                override fun onCancelled(p0: DatabaseError) {
                    Utils.showLog("MyTikets Error: " + p0.message)
                }
            })

            databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    restOfBalance = myBalance - valueTotalHarga
                    databaseUsers.ref.child("user_balance").setValue(restOfBalance)
                    Utils.showLog("User Balance Rest of Balance: $restOfBalance")
                }
                override fun onCancelled(p0: DatabaseError) {
                    Utils.showLog("User Balance Error: " + p0.message)
                }
            })
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}
