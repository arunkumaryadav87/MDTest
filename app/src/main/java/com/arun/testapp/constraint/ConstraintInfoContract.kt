package com.arun.testapp.constraint

import com.arun.testapp.base.BasePresenter
import com.arun.testapp.base.BaseView
import com.arun.testapp.viewmodel.PackageConstraint

interface ConstraintInfoContract {
    interface View : BaseView {
        fun onSuccess()
        fun onFail()
        fun showConstraint(packageConstraint: PackageConstraint)
    }

    interface Presenter : BasePresenter{
        fun saveConstraint(packageConstraint: PackageConstraint)
        fun getConstraint()
    }
}