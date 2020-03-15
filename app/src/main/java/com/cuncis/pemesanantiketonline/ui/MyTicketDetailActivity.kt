package com.cuncis.pemesanantiketonline.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.util.Constants.KEY_WISATA
import com.cuncis.pemesanantiketonline.util.Utils.Companion.showLog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_my_ticket_detail.*

class MyTicketDetailActivity : AppCompatActivity() {

    private lateinit var databaseWisata: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ticket_detail)

        val bundle = intent.extras
        val namaWisata = bundle?.getString(KEY_WISATA).toString()

        databaseWisata = FirebaseDatabase.getInstance().reference.child("Wisata").child(namaWisata)
        databaseWisata.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv_nama_wisata.text = dataSnapshot.child("nama_wisata").value.toString()
                tv_lokasi.text = dataSnapshot.child("lokasi").value.toString()
                tv_time_wisata.text = dataSnapshot.child("time_wisata").value.toString()
                tv_date_wisata.text = dataSnapshot.child("date_wisata").value.toString()
                tv_ketentuan.text = dataSnapshot.child("ketentuan").value.toString()
            }
            override fun onCancelled(p0: DatabaseError) {
                showLog("" + p0.message)
            }
        })

        btn_back.setOnClickListener {
            finish()
        }

    }
}
