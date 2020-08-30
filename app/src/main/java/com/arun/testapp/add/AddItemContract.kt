package com.arun.testapp.add

import com.arun.testapp.base.BasePresenter
import com.arun.testapp.base.BaseView
import com.arun.testapp.db.entity.Item

interface AddItemContract {
    interface View : BaseView {
        fun success()
        fun failed()
    }

    interface Presenter : BasePresenter{
        fun saveItem(item: Item)
    }
}