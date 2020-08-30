package com.arun.testapp.constraint

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.arun.testapp.R
import com.arun.testapp.base.BaseFragment
import com.arun.testapp.viewmodel.PackageConstraint
import kotlinx.android.synthetic.main.fragment_constraint_info.*
import kotlinx.android.synthetic.main.layout_app_bar.*

class ConstraintInfoFragment : BaseFragment(), ConstraintInfoContract.View {
    private lateinit var sortAdapter: ArrayAdapter<CharSequence>
    private lateinit var presenter: ConstraintInfoPresenter

    override fun getScreenName() = TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializePresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_constraint_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun initializeView() {
        setTitle()
        sortAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.sort_orders,
            android.R.layout.simple_spinner_item
        )
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSortOrder.adapter = sortAdapter

        btnShowConstraint.setOnClickListener {
            Log.d(TAG, "Save constraint called")
            if (etPiece.text!!.isEmpty()) {
                etPiece.error = "Cannot be blank"
                return@setOnClickListener
            }

            if (etWeight.text!!.isEmpty()) {
                etWeight.error = "Cannot be blank"
                return@setOnClickListener
            }

            presenter.saveConstraint(
                PackageConstraint(
                    spinnerSortOrder.selectedItem.toString(),
                    etPiece.text.toString().toInt(),
                    etWeight.text.toString()
                )
            )
        }

        presenter.getConstraint()
    }

    private fun initializePresenter() {
        presenter = ConstraintInfoPresenter(this, sharedPreferences!!)
    }

    override fun onSuccess() {
        tvResult.visibility = View.VISIBLE
        tvResult.text = getString(R.string.saved_successfully)
    }

    override fun onFail() {
        tvResult.visibility = View.GONE
        tvResult.text = getString(R.string.failed_to_save)
    }

    override fun showConstraint(packageConstraint: PackageConstraint) {
        val spinnerPosition: Int = sortAdapter.getPosition(packageConstraint.sortOrder)
        spinnerSortOrder.setSelection(spinnerPosition)
        etPiece.setText(packageConstraint.pieceLimit.toString())
        etWeight.setText(packageConstraint.weightLimit.toString())
    }

    override fun showProgress() {
        pgBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pgBar.visibility = View.GONE
    }

    override fun setTitle() {
        activity!!.toolbar_title.text = getString(R.string.constraint_info)
    }

    companion object {
        private const val TAG = "ConstraintInfoFragment"
    }
}