package com.arun.testapp.packlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arun.testapp.R
import com.arun.testapp.itemlist.ItemAdapter
import com.arun.testapp.viewmodel.PackageModel
import kotlinx.android.synthetic.main.package_cell.view.*

class PackageAdapter(var context: Context, var packages: List<PackageModel>) :
    RecyclerView.Adapter<PackageAdapter.ViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            inflater.inflate(
                R.layout.package_cell,
                parent,
                false
            )
        )

    override fun getItemCount() = packages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(packages[position])

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(item: PackageModel) {
            itemView.tvPackageNumber.text = String.format(
                itemView.resources.getString(R.string.package_number),
                adapterPosition + 1
            )

            itemView.rvPackageItems.layoutManager = LinearLayoutManager(
                itemView.context,
                RecyclerView.VERTICAL, false
            )

            val itemAdapter = ItemAdapter(itemView.context, item.items)
            itemView.rvPackageItems.adapter = itemAdapter
            itemAdapter.notifyDataSetChanged()

            itemView.tvPackageLength.text = String.format(
                itemView.resources.getString(R.string.package_length),
                item.getMaxLength().toString()
            )

            itemView.tvPackageWeight.text = String.format(
                itemView.resources.getString(R.string.package_weight),
                item.getTotalWeight().toString()
            )
        }
    }
}