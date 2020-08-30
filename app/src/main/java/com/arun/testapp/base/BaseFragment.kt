package com.arun.testapp.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.arun.testapp.MainApplication

abstract class BaseFragment : Fragment() {
    internal var sharedPreferences: SharedPreferences? = null
    abstract fun getScreenName(): String?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = (activity?.application as MainApplication).getSharedPreferences()
    }
}