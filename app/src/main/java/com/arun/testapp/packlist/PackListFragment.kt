package com.arun.testapp.packlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arun.testapp.R
import com.arun.testapp.base.BaseFragment
import com.arun.testapp.db.AppDatabase
import com.arun.testapp.preference.PreferenceHelper.Companion.packPieceLimit
import com.arun.testapp.preference.PreferenceHelper.Companion.packSortOrder
import com.arun.testapp.preference.PreferenceHelper.Companion.packWeightLimit
import com.arun.testapp.utils.rx.SchedulerProviderImpl
import com.arun.testapp.viewmodel.PackageConstraint
import com.arun.testapp.viewmodel.PackageModel
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.android.synthetic.main.fragment_pack_list.*
import kotlinx.android.synthetic.main.fragment_pack_list.pgBar
import kotlinx.android.synthetic.main.layout_app_bar.*

class PackListFragment : BaseFragment(), PackListContract.View {
    private lateinit var presenter: PackListPresenter
    private var appDatabase: AppDatabase? = null
    private lateinit var packageAdapter: PackageAdapter

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
        return inflater.inflate(R.layout.fragment_pack_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()

        val sortOrder = sharedPreferences?.packSortOrder
        val pieceLimit = sharedPreferences?.packPieceLimit
        val weightLimit = sharedPreferences?.packWeightLimit

        if (!sortOrder.isNullOrEmpty()) presenter.getPackages(
            PackageConstraint(
                sortOrder,
                pieceLimit,
                weightLimit
            )
        )
    }

    override fun showProgress() {
        pgBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pgBar.visibility = View.GONE
    }

    override fun setTitle() {
        activity!!.toolbar_title.text = getString(R.string.show_pack_list)
    }

    private fun initializePresenter() {
        presenter = PackListPresenter(this, appDatabase!!, SchedulerProviderImpl())
    }

    private fun initializeView() {
        setTitle()
        rvPackList.layoutManager = LinearLayoutManager(
            context!!,
            RecyclerView.VERTICAL,
            false
        )
    }

    override fun showPackages(packages: List<PackageModel>) {
        Log.d(TAG, "Package list formation successful")
        Log.d(TAG, "Number of Packages: ${packages.size}")
        packageAdapter = PackageAdapter(activity!!, packages)
        rvPackList.adapter = packageAdapter
        packageAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        presenter.dropView()
        super.onDestroyView()
    }

    companion object {
        private const val TAG = "PackListFragment"
    }
}