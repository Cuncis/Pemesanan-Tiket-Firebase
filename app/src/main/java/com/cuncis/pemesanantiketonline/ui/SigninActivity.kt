package com.cuncis.pemesanantiketonline.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.data.PrefsManager
import com.cuncis.pemesanantiketonline.util.Utils
import com.cuncis.pemesanantiketonline.util.Utils.Companion.showLog
import com.cuncis.pemesanantiketonline.util.Utils.Companion.showToast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        btn_sign_in.setOnClickListener {
            database = FirebaseDatabase.getInstance().reference
                .child("Users").child(et_username.text.toString())

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child("username").value.toString() == et_username.text.toString()) {
                        val password = dataSnapshot.child("password").value.toString()

                        if (et_password.text.toString() == password) {
                            PrefsManager.setUsername(this@SigninActivity, et_username.text.toString().trim())

                            startActivity(Intent(this@SigninActivity, HomeActivity::class.java))
                        } else {
                            showToast(applicationContext, "Password Incorrect!")
                        }
                    } else {
                        showToast(applicationContext, "Username Not Found!")
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    showLog("" + p0.message)
                }
            })
        }
        btn_new_account.setOnClickListener {
            startActivity(Intent(this, RegisterOneActivity::class.java))
        }
    }
}
