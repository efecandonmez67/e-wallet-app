package com.example.ewalletapp.network

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences("ewallet_prefs", Context.MODE_PRIVATE)
        }
    }

    fun saveToken(token: String) {
        prefs?.edit()?.putString("USER_TOKEN", token)?.apply()
    }

    fun getToken(): String? {
        return prefs?.getString("USER_TOKEN", null)
    }

    fun clearToken() {
        prefs?.edit()?.remove("USER_TOKEN")?.apply()
    }
}