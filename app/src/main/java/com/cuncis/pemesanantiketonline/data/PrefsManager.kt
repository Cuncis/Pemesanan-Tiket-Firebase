package com.cuncis.pemesanantiketonline.data

import android.content.Context
import android.content.SharedPreferences
import com.cuncis.pemesanantiketonline.util.Constants.KEY_USERNAME
import com.cuncis.pemesanantiketonline.util.Constants.PREF_NAME

class PrefsManager {
    companion object {
        private fun getSharedPreference(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        fun setUsername(context: Context, username: String) {
            val editor = getSharedPreference(context).edit()
            editor.putString(KEY_USERNAME, username)
            editor.apply()
        }

        fun getUsername(context: Context): String? {
            return getSharedPreference(context).getString(KEY_USERNAME, "")
        }

        fun clearUser(context: Context) {
            val editor = getSharedPreference(context).edit()
            editor.clear()
            editor.apply()
        }
    }

}