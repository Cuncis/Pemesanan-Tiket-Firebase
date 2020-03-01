package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.data.PrefsManager
import com.cuncis.pemesanantiketonline.util.Constants.KEY_TICKET
import com.cuncis.pemesanantiketonline.util.Utils
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        database = FirebaseDatabase.getInstance().reference
            .child("Users").child(PrefsManager.getUsername(this).toString())

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv_nama_lengkap.text = dataSnapshot.child("nama_lengkap").value.toString()
                tv_bio.text = dataSnapshot.child("bio").value.toString()
                tv_user_balance.text = "$ ${dataSnapshot.child("user_balance").value.toString()}"
                Picasso.with(this@HomeActivity).load(dataSnapshot.child("url_photo_profile").value.toString())
                    .centerCrop().fit().into(img_photo_home_user)
            }
            override fun onCancelled(p0: DatabaseError) {
                Utils.showLog("" + p0.message)
            }
        })

        btn_to_profile.setOnClickListener {
            startActivity(Intent(this, MyProfileActivity::class.java))
        }

        btn_ticket_pisa.setOnClickListener {
            val gotoPisa = Intent(this, TicketDetailActivity::class.java)
            gotoPisa.putExtra(KEY_TICKET, "Pisa")
            startActivity(gotoPisa)
        }

        btn_ticket_torri.setOnClickListener {
            val gotoTorri = Intent(this, TicketDetailActivity::class.java)
            gotoTorri.putExtra(KEY_TICKET, "Torri")
            startActivity(gotoTorri)
        }

        btn_ticket_pagoda.setOnClickListener {
            val gotoPagoda = Intent(this, TicketDetailActivity::class.java)
            gotoPagoda.putExtra(KEY_TICKET, "Pagoda")
            startActivity(gotoPagoda)
        }

        btn_ticket_candi.setOnClickListener {
            val gotoCandi = Intent(this, TicketDetailActivity::class.java)
            gotoCandi.putExtra(KEY_TICKET, "Candi")
            startActivity(gotoCandi)
        }

        btn_ticket_sphinx.setOnClickListener {
            val gotoSphinx = Intent(this, TicketDetailActivity::class.java)
            gotoSphinx.putExtra(KEY_TICKET, "Sphinx")
            startActivity(gotoSphinx)
        }

        btn_ticket_monas.setOnClickListener {
            val gotoMonas = Intent(this, TicketDetailActivity::class.java)
            gotoMonas.putExtra(KEY_TICKET, "Monas")
            startActivity(gotoMonas)
        }
    }
}



















