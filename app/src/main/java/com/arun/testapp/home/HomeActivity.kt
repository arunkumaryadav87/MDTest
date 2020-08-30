package com.arun.testapp.home

import android.os.Bundle
import com.arun.testapp.R
import com.arun.testapp.base.BaseActivity

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.contentFrame, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun getScreenName() = TAG

    companion object {
        private const val TAG = "HomeActivity"
    }
}