package com.works.crm_project.manager

import android.content.Context
import android.preference.PreferenceManager

class TokenManager(context : Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveData(token: String,id : Int, email : String) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.putInt(ID_KEY,id)
        editor.putString(EMAIL_KEY,email)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun getId() : Int {
        return sharedPreferences.getInt(ID_KEY, 0)
    }

    fun getEmail() : String? {
        return sharedPreferences.getString(EMAIL_KEY, null)
    }

    fun clearData() {
        val editor = sharedPreferences.edit()
        editor.remove(TOKEN_KEY)
        editor.remove(ID_KEY)
        editor.remove(EMAIL_KEY)
        editor.apply()
    }

    companion object {
        private const val TOKEN_KEY = "token"
        private const val ID_KEY = "userId"
        private const val EMAIL_KEY = "email"
    }

}