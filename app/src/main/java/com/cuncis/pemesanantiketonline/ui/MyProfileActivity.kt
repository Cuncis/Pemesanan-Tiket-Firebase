package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.data.PrefsManager
import com.cuncis.pemesanantiketonline.data.model.MyTicket
import com.cuncis.pemesanantiketonline.util.Constants
import com.cuncis.pemesanantiketonline.util.Constants.KEY_WISATA
import com.cuncis.pemesanantiketonline.util.Utils.Companion.showLog
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : AppCompatActivity(), MyProfileAdapter.ClickListener {

    private lateinit var databaseUsers: DatabaseReference
    private lateinit var databaseMyTickets: DatabaseReference

    private lateinit var myProfileAdapter: MyProfileAdapter
    private var ticketList: ArrayList<MyTicket> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        myProfileAdapter = MyProfileAdapter(this)
        rv_myticket_place.layoutManager = LinearLayoutManager(this)
        rv_myticket_place.setHasFixedSize(true)
        myProfileAdapter.setClickListener(this)
        rv_myticket_place.adapter = myProfileAdapter

        databaseUsers = FirebaseDatabase.getInstance().reference.child("Users")
            .child(PrefsManager.getUsername(this).toString())
        databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv_nama_lengkap.text = dataSnapshot.child("nama_lengkap").value.toString()
                tv_bio.text = dataSnapshot.child("bio").value.toString()
                Picasso.with(this@MyProfileActivity).load(dataSnapshot.child("url_photo_profile").value.toString())
                    .into(photo_profile)
            }
            override fun onCancelled(p0: DatabaseError) {
                showLog("MyProfile Users Error: " + p0.message)
            }
        })

        databaseMyTickets = FirebaseDatabase.getInstance().reference.child("MyTikets")
            .child(PrefsManager.getUsername(this).toString())
        databaseMyTickets.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
                    ticketList.add(data.getValue(MyTicket::class.java)!!)
                    showLog("MyProfile Ticket List: $ticketList")
                }

                myProfileAdapter.setTicketList(ticketList)
            }
            override fun onCancelled(p0: DatabaseError) {
                showLog("MyProfile Tickets Error: " + p0.message)
            }
        })

        btn_back_home.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

    override fun onTicketClick(position: Int) {
        val intent = Intent(this, MyTicketDetailActivity::class.java)
        intent.putExtra(KEY_WISATA, ticketList[position].nama_wisata)
        startActivity(intent)
    }
}
