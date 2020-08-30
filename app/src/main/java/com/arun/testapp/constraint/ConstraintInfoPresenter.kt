package com.arun.testapp.constraint

import android.content.SharedPreferences
import android.util.Log
import com.arun.testapp.viewmodel.PackageConstraint
import com.arun.testapp.preference.PreferenceHelper.Companion.packPieceLimit
import com.arun.testapp.preference.PreferenceHelper.Companion.packSortOrder
import com.arun.testapp.preference.PreferenceHelper.Companion.packWeightLimit

class ConstraintInfoPresenter(
    var view: ConstraintInfoContract.View,
    var sharedPreferences: SharedPreferences
) : ConstraintInfoContract.Presenter {
    override fun saveConstraint(packageConstraint: PackageConstraint) {
        view.showProgress()
        try {
            sharedPreferences.packSortOrder = packageConstraint.sortOrder
            sharedPreferences.packPieceLimit = packageConstraint.pieceLimit!!
            sharedPreferences.packWeightLimit = packageConstraint.weightLimit!!

            Log.d("Presenter", sharedPreferences.packSortOrder!!)
            view.onSuccess()
        } catch (e: Exception) {
            view.onFail()
        }
        view.hideProgress()
    }

    override fun getConstraint() {
        sharedPreferences.packSortOrder?.let {
            view.showProgress()
            view.showConstraint(
                PackageConstraint(
                    it,
                    sharedPreferences.packPieceLimit,
                    sharedPreferences.packWeightLimit
                )
            )
            view.hideProgress()
        }
    }

    override fun dropView() {
    }
}