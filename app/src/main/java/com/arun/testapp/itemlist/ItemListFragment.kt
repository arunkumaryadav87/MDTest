package com.arun.testapp.itemlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arun.testapp.R
import com.arun.testapp.add.AddItemFragment
import com.arun.testapp.add.AddItemPresenter
import com.arun.testapp.base.BaseFragment
import com.arun.testapp.db.AppDatabase
import com.arun.testapp.db.entity.Item
import com.arun.testapp.utils.rx.SchedulerProviderImpl
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.android.synthetic.main.layout_app_bar.*

class ItemListFragment : BaseFragment(), ItemListContract.View {
    private lateinit var presenter: ItemListPresenter
    private var appDatabase: AppDatabase? = null
    private lateinit var itemAdapter: ItemAdapter

    override fun getScreenName() = TAG

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
        return inflater.inflate(R.layout.fragment_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        presenter.getItems()
    }

    private fun initializeView() {
        setTitle()
        rvItemList.layoutManager = LinearLayoutManager(
            context!!,
            RecyclerView.VERTICAL,
            false)
    }

    private fun initializePresenter() {
        presenter = ItemListPresenter(this, appDatabase!!, SchedulerProviderImpl())
    }

    override fun showItems(items: List<Item>) {
        Log.d(TAG, "Items retrieval successful")
        Log.d(TAG, "Items length: ${items.size}")
        itemAdapter = ItemAdapter(activity!!, items)
        rvItemList.adapter = itemAdapter
        itemAdapter.notifyDataSetChanged()
    }

    override fun showProgress() {
        pgBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pgBar.visibility = View.GONE
    }

    override fun setTitle() {
        activity!!.toolbar_title.text = getString(R.string.show_item_list)
    }

    companion object {
        private const val TAG = "ItemListFragment"
    }
}