package com.arun.testapp.itemlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arun.testapp.R
import com.arun.testapp.db.entity.Item
import kotlinx.android.synthetic.main.item_cell.view.*

class ItemAdapter(var context: Context, var items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(inflater.inflate(
            R.layout.item_cell,
            parent,
            false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(item: Item) {
            itemView.tvItemID.text = String.format(
                itemView.resources.getString(R.string.item_id_label),
                item.itemID.toString()
            )
            itemView.tvItemLength.text = String.format(
                itemView.resources.getString(R.string.item_length_label),
                item.itemLength.toString()
            )
            itemView.tvItemQuantity.text = String.format(
                itemView.resources.getString(R.string.item_quantity_label),
                item.itemQuantity.toString()
            )
            itemView.tvItemWeight.text = String.format(
                itemView.resources.getString(R.string.item_weight_label),
                item.itemWeight.toString()
            )
        }
    }
}