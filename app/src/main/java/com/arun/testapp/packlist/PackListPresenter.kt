package com.arun.testapp.packlist

import com.arun.testapp.db.AppDatabase
import com.arun.testapp.db.dao.ItemDao
import com.arun.testapp.db.entity.Item
import com.arun.testapp.utils.rx.SchedulerProvider
import com.arun.testapp.viewmodel.PackageConstraint
import com.arun.testapp.viewmodel.PackageModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class PackListPresenter(
    var view: PackListContract.View,
    var appDatabase: AppDatabase,
    var schedulerProvider: SchedulerProvider
) : PackListContract.Presenter {

    private var itemDao: ItemDao? = null
    private var compositeDisposable: CompositeDisposable? = null
    private var packageList = arrayListOf<PackageModel>()
    lateinit var packageConstraint: PackageConstraint

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun getPackages(packageConstraint: PackageConstraint) {
        this.packageConstraint = packageConstraint
        view.showProgress()

        val disposable = Single.fromCallable {
            itemDao = appDatabase.itemDao()
            if (packageConstraint.sortOrder == "NATURAL") {
                appDatabase.itemDao().getItems()
            } else {
                appDatabase.itemDao().getItemsSorted(packageConstraint.sortOrder == "SHORT_TO_LONG")
            }
        }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe { items ->
                if (items.isNotEmpty()) {
                    generatePackage(items)
                }
            }

        compositeDisposable?.add(disposable)
        view.hideProgress()
    }

    private fun generatePackage(items: List<Item>) {
        val pieceLimit = packageConstraint.pieceLimit
        val weightLimit = packageConstraint.weightLimit

        val packageItems = ArrayList<Item>()

        var packageQuantity = 0
        var quantity = 0
        var weight = 0.0

        for (item in items) {
            for (qty in 1..item.itemQuantity) {
                quantity++
                weight += item.itemWeight

                if (qty == item.itemQuantity) {
                    packageItems.add(Item(item.itemID, item.itemLength, quantity, item.itemWeight))
                    if (item.itemID == items.last().itemID) packageList.add(
                        PackageModel(
                            packageItems.toList()
                        )
                    )
                    packageQuantity = quantity
                    quantity = 0
                } else if (packageQuantity + quantity >= pieceLimit!! || weight >= weightLimit!!.toDouble()) {
                    packageItems.add(Item(item.itemID, item.itemLength, quantity, item.itemWeight))
                    packageQuantity = 0
                    quantity = 0
                    weight = 0.0
                    packageList.add(PackageModel(packageItems.toList()))
                    packageItems.clear()
                }
            }
        }

        view.showPackages(packageList!!)
    }

    override fun dropView() {
        compositeDisposable?.clear()
    }

    companion object {
        private const val TAG = "PackListPresenter"
    }
}