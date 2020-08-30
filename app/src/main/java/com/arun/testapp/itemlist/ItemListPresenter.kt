package com.arun.testapp.itemlist

import com.arun.testapp.db.AppDatabase
import com.arun.testapp.db.dao.ItemDao
import com.arun.testapp.utils.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class ItemListPresenter(
    var view: ItemListContract.View,
    var appDatabase: AppDatabase,
    var schedulerProvider: SchedulerProvider
) : ItemListContract.Presenter {
    private var itemDao: ItemDao? = null
    private var compositeDisposable: CompositeDisposable? = null

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun getItems() {
        view.showProgress()

        val disposable = Single.fromCallable {
            itemDao = appDatabase.itemDao()
            appDatabase.itemDao().getItems()
//            appDatabase.itemDao().getItemsSorted(true)
//            appDatabase.itemDao().getItemsSorted(false)
        }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe { items ->
                if (items.isNotEmpty()) {
                    view.showItems(items)
                }
            }

        compositeDisposable?.add(disposable)
        view.hideProgress()
    }

    override fun dropView() {
        compositeDisposable?.clear()
    }

}