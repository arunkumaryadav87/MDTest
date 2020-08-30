package com.arun.testapp.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arun.testapp.R
import com.arun.testapp.add.AddItemFragment
import com.arun.testapp.base.BaseFragment
import com.arun.testapp.constraint.ConstraintInfoFragment
import com.arun.testapp.itemlist.ItemListFragment
import com.arun.testapp.packlist.PackListFragment
import com.arun.testapp.preference.PreferenceHelper.Companion.packSortOrder
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun getScreenName() = TAG

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Sort Order: ${sharedPreferences?.packSortOrder}");

        if (sharedPreferences?.packSortOrder.isNullOrEmpty()) {
            btnShowConstraint.text = getString(R.string.show_constraints)
        } else {
            btnShowConstraint.text = getString(R.string.edit_constraints)
        }

        btnShowConstraint.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(R.id.contentFrame, ConstraintInfoFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        btnAddItem.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(R.id.contentFrame, AddItemFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        btnShowItemList.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(R.id.contentFrame, ItemListFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        btnShowPackList.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.add(R.id.contentFrame, PackListFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}