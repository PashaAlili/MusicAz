package com.example.musicaz.localstorage

import android.content.Context
import android.content.SharedPreferences

object User {

    fun saveUser(context: Context, email: String, uid: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("spRememberMe", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("uid", uid)
        editor.apply()
    }

    fun deleteUser(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("spRememberMe", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("uid", null)
        editor.apply()
    }

    fun readLastUserEmail(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("spRememberMe", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", null)
    }

    fun hasUser(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("spRememberMe", Context.MODE_PRIVATE)
        return !sharedPreferences.getString("uid", null).isNullOrBlank()
    }

}