package com.arun.testapp.packlist

import com.arun.testapp.base.BasePresenter
import com.arun.testapp.base.BaseView
import com.arun.testapp.viewmodel.PackageConstraint
import com.arun.testapp.viewmodel.PackageModel

interface PackListContract {
    interface View : BaseView {
        fun showPackages(packages: List<PackageModel>)
    }

    interface Presenter : BasePresenter {
        fun getPackages(packageConstraint: PackageConstraint)
    }
}