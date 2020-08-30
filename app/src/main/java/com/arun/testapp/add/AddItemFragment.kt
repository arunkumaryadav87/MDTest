package com.arun.testapp.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arun.testapp.R
import com.arun.testapp.base.BaseFragment
import com.arun.testapp.db.AppDatabase
import com.arun.testapp.db.entity.Item
import com.arun.testapp.itemlist.ItemListFragment
import com.arun.testapp.utils.rx.SchedulerProviderImpl
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.android.synthetic.main.layout_app_bar.*

class AddItemFragment : BaseFragment(), AddItemContract.View {
    private lateinit var presenter: AddItemPresenter
    private var appDatabase: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDatabase = AppDatabase.getAppDatabase(context = activity!!)
        initializePresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    override fun getScreenName() = TAG

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
    }

    private fun initializeViews() {
        setTitle()
        btnShowConstraint.setOnClickListener {
            if (etItemID.text.toString().isEmpty()) {
                etItemID.error = "Item Id cannot be blank"
                return@setOnClickListener
            }
            if (etPiece.text.toString().isEmpty()) {
                etPiece.error = "Item length cannot be blank"
                return@setOnClickListener
            }
            if (etQuantity.text.toString().isEmpty()) {
                etQuantity.error = "Item quantity cannot be blank"
                return@setOnClickListener
            }
            if (etWeight.text.toString().isEmpty()) {
                etWeight.error = "Item weight cannot be blank"
                return@setOnClickListener
            }

            presenter.saveItem(
                Item(
                    itemID = etItemID.text.toString().toInt(),
                    itemLength = etPiece.text.toString().toInt(),
                    itemQuantity = etQuantity.text.toString().toInt(),
                    itemWeight = etWeight.text.toString().toDouble()
                )
            )
        }
    }

    private fun initializePresenter() {
        presenter = AddItemPresenter(this, appDatabase!!, SchedulerProviderImpl())
    }

    override fun success() {
        Log.d(TAG, "Item saved successful")
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.contentFrame, ItemListFragment())
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun failed() {
        Log.d(TAG, "Item saving failed")
    }

    override fun showProgress() {
        pgBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pgBar.visibility = View.GONE
    }

    override fun setTitle() {
        activity!!.toolbar_title.text = getString(R.string.add_item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }

    companion object {
        private const val TAG = "AddItemFragment"
    }
}