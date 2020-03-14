package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.util.Constants.KEY_TICKET
import com.cuncis.pemesanantiketonline.util.Utils
import com.cuncis.pemesanantiketonline.util.Utils.Companion.showLog
import com.cuncis.pemesanantiketonline.util.Utils.Companion.showToast
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ticket_detail.*

class TicketDetailActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_detail)

        val bundle = intent.extras
        val newTicket = bundle?.getString(KEY_TICKET)

        database = FirebaseDatabase.getInstance().reference.child("Wisata")
            .child(newTicket.toString())
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                title_ticket.text = dataSnapshot.child("nama_wisata").value.toString()
                location_ticket.text = dataSnapshot.child("lokasi").value.toString()
                photo_spot_ticket.text = dataSnapshot.child("is_photo_spot").value.toString()
                wifi_ticket.text = dataSnapshot.child("is_wifi").value.toString()
                festival_ticket.text = dataSnapshot.child("is_festival").value.toString()
                short_desc_ticket.text = dataSnapshot.child("short_desc").value.toString()
                Picasso.with(this@TicketDetailActivity).load(dataSnapshot.child("url_thumbnail").value.toString())
                    .centerCrop().fit().into(header_ticket_detail)
            }
            override fun onCancelled(p0: DatabaseError) {
                showLog("" + p0.message)
            }
        })

        btn_buy_ticket.setOnClickListener {
            val intent = Intent(this, TicketCheckoutActivity::class.java)
            intent.putExtra(KEY_TICKET, newTicket)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}
