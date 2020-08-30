package com.arun.testapp.itemlist

import com.arun.testapp.base.BasePresenter
import com.arun.testapp.base.BaseView
import com.arun.testapp.db.entity.Item

interface ItemListContract {
    interface View : BaseView {
        fun showItems(items: List<Item>);
    }

    interface Presenter : BasePresenter {
        fun getItems();
    }
}