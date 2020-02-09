package com.cuncis.pemesanantiketonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_one.*

class RegisterOneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_one)

        btn_continue.setOnClickListener {
            startActivity(Intent(this, RegisterTwoActivity::class.java))
        }

        btn_back.setOnClickListener {
            finish()
        }
    }
}
