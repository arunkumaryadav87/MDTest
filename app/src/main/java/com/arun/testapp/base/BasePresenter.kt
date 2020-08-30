package com.arun.testapp.base

interface BasePresenter {
    // drop view will allow to handle memory leaks
    fun dropView()
}