package com.pkrob.ApiDocLoL.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPreferencesManager {

    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_PO = "po"

    fun saveUserId(context: Context, userId: String) {
        Log.d("SharedPreferencesManager", "Saving userId: $userId")
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    fun getUserId(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userId = prefs.getString(KEY_USER_ID, null)
        Log.d("SharedPreferencesManager", "Retrieved userId: $userId")
        return userId
    }

    fun savePO(context: Context, po: Int) {
        Log.d("SharedPreferencesManager", "Saving PO: $po")
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt(KEY_PO, po)
        editor.apply()
    }

    fun getPO(context: Context): Int {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val po = prefs.getInt(KEY_PO, 0)
        Log.d("SharedPreferencesManager", "Retrieved PO: $po")
        return po
    }
}
