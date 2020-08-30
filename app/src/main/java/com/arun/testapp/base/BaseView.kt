package com.arun.testapp.base

import android.content.res.Resources

interface BaseView {
    fun getString(id: Int): String?

    fun showProgress()

    fun hideProgress()

    fun setTitle()
}