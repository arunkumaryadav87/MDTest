package com.arun.testapp.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Item")
data class Item(
    @PrimaryKey
    @NonNull
    val itemID: Int,
    val itemLength: Int,
    val itemQuantity: Int,
    val itemWeight: Double
)