package com.arun.testapp.add

import com.arun.testapp.db.AppDatabase
import com.arun.testapp.db.dao.ItemDao
import com.arun.testapp.db.entity.Item
import com.arun.testapp.utils.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class AddItemPresenter(var view: AddItemContract.View, var appDatabase: AppDatabase, private var schedulerProvider: SchedulerProvider) : AddItemContract.Presenter {
    private var itemDao: ItemDao? = null
    private var compositeDisposable: CompositeDisposable? = null

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun saveItem(item: Item) {
        view.showProgress()
        val disposable = Single
            .fromCallable {
                itemDao = appDatabase.itemDao()
                appDatabase.itemDao().insertItem(item)
                view.success()
                ""
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe()


        compositeDisposable?.add(disposable)
        view.hideProgress()
    }

    override fun dropView() {
        compositeDisposable?.clear()
    }
}