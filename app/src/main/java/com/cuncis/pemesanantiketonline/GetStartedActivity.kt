package com.cuncis.pemesanantiketonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_get_started.*

class GetStartedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        btn_sign_in.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

        btn_new_account_create.setOnClickListener {
            startActivity(Intent(this, RegisterOneActivity::class.java))
        }
    }
}
