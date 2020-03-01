package com.cuncis.pemesanantiketonline.util

import android.content.Context
import android.util.Log
import android.widget.Toast

class Utils {
    companion object {
        fun showLog(message: String) {
            Log.d("_tiketSaya", message)
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}