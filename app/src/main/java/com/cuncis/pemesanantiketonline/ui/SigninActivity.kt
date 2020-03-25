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

            btn_sign_in.isEnabled = false
            btn_sign_in.text = "LOADING..."

            if (et_username.text.toString().isEmpty() && et_password.text.toString().isEmpty()) {
                showToast(applicationContext, "Username and Password can't be empty!")
                btn_sign_in.isEnabled = true
                btn_sign_in.text = "SIGN IN"
            } else if (et_username.text.toString().isEmpty()) {
                showToast(applicationContext, "Username can't be empty!")
                btn_sign_in.isEnabled = true
                btn_sign_in.text = "SIGN IN"
            } else if (et_password.text.toString().isEmpty()) {
                showToast(applicationContext, "Password can't be empty!")
                btn_sign_in.isEnabled = true
                btn_sign_in.text = "SIGN IN"
            } else {
                database = FirebaseDatabase.getInstance().reference
                    .child("Users").child(et_username.text.toString())

                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.child("username").value.toString() == et_username.text.toString()) {
                            val password = dataSnapshot.child("password").value.toString()

                            if (et_password.text.toString() == password) {
                                PrefsManager.setUsername(this@SigninActivity, et_username.text.toString().trim())

                                val intent = Intent(this@SigninActivity, HomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                finish()
                            } else {
                                showToast(applicationContext, "Password Incorrect!")
                                btn_sign_in.isEnabled = true
                                btn_sign_in.text = "SIGN IN"
                            }
                        } else {
                            showToast(applicationContext, "Username Not Found!")
                            btn_sign_in.isEnabled = true
                            btn_sign_in.text = "SIGN IN"
                        }
                    }
                    override fun onCancelled(p0: DatabaseError) {
                        showLog("" + p0.message)
                        btn_sign_in.isEnabled = true
                        btn_sign_in.text = "SIGN IN"
                    }
                })
            }
        }
        btn_new_account.setOnClickListener {
            startActivity(Intent(this, RegisterOneActivity::class.java))
        }
    }
}
