package com.arun.testapp.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arun.testapp.MainApplication

abstract class BaseActivity : AppCompatActivity() {
    abstract fun getScreenName(): String?
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = (application as MainApplication).getSharedPreferences()
    }

    companion object {
        private const val TAG = "BaseActivity"
    }
}