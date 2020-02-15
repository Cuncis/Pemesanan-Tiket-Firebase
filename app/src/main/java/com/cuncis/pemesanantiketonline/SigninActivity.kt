package com.cuncis.pemesanantiketonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        btn_sign_in.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        btn_new_account.setOnClickListener {
            startActivity(Intent(this, RegisterOneActivity::class.java))
        }
    }
}
