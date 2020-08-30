package com.arun.testapp.viewmodel

import com.arun.testapp.db.entity.Item

class PackageModel(var items: List<Item>)  {

    fun getMaxLength(): Int {
        var maxLength = 0
        for(item in items) {
            if (item.itemLength > maxLength) maxLength = item.itemLength
        }
        return maxLength
    }

    fun getTotalWeight(): Double {
        var totalWeight = 0.0
        for(item in items) {
            totalWeight += (item.itemQuantity * item.itemWeight)
        }
        return "%.2f".format(totalWeight).toDouble()
    }
}
