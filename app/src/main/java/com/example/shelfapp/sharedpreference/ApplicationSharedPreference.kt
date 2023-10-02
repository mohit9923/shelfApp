package com.example.shelfapp.sharedpreference

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.shelfapp.application.BookShelfApplication


@SuppressLint("ApplySharedPref")
class ApplicationSharedPreference {
    var sharedPreferences: SharedPreferences? = null

    companion object{
        var instance1: ApplicationSharedPreference? = null
        fun getInstance(): ApplicationSharedPreference? {
            if (instance1 == null) {
                synchronized(ApplicationSharedPreference::class.java) {
                    instance1 = ApplicationSharedPreference()
                }
            }
            return instance1
        }
    }


    fun saveData(key: String?, dataToSave: String?) {
        sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(BookShelfApplication.getInstance())
        val editor = sharedPreferences?.edit()
        if (dataToSave != null) editor?.putString(key, dataToSave)
        editor?.commit()
    }

    fun getValueOfSharedPrefrences(context: Activity?, key: String?): String? {
        if (key != null && context != null) {
            sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            return sharedPreferences?.getString(key, null)
        }
        return null
    }

    fun clearPreferences() {
        if (sharedPreferences == null) sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(BookShelfApplication.getInstance())

        //Clear the preferences
        sharedPreferences!!.edit().clear().commit()
    }


}