package com.arun.testapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.arun.testapp.preference.PreferenceHelper

class MainApplication : Application() {
    private var appContext: Context? = null
    private var sharedPreferences: SharedPreferences? = null

    fun getAppContext() = appContext

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        sharedPreferences = PreferenceHelper.customPreference(context = this, name = APP_PREFERENCES)
    }

    fun getSharedPreferences(): SharedPreferences? {
        if (sharedPreferences == null) sharedPreferences = PreferenceHelper.customPreference(context = this, name = APP_PREFERENCES)
        return sharedPreferences
    }

    companion object {
        private const val TAG = "MainApplication"
        private const val APP_PREFERENCES = "APP_PREFERENCES"
    }
}