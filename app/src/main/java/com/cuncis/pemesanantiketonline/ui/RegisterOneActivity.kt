package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.data.PrefsManager
import com.cuncis.pemesanantiketonline.util.Utils.Companion.showLog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register_one.*

class RegisterOneActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_one)

        showLog(PrefsManager.getUsername(this).toString())
        btn_continue.setOnClickListener {
            btn_continue.isEnabled = false
            btn_continue.text = "Loading..."

            PrefsManager.setUsername(this, et_username.text.toString())
            database = FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(et_username.text.toString())

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("username").setValue(et_username.text.toString())
                    dataSnapshot.ref.child("password").setValue(et_password.text.toString())
                    dataSnapshot.ref.child("email_address").setValue(et_email_address.text.toString())
                    dataSnapshot.ref.child("user_balance").setValue(800)
                }
                override fun onCancelled(p0: DatabaseError) {
                    showLog("" + p0.message)
                }
            })

            startActivity(Intent(this, RegisterTwoActivity::class.java))
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}
