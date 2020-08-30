package com.arun.testapp.db.dao

import androidx.room.*
import com.arun.testapp.db.entity.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Query("SELECT * FROM Item WHERE itemID == :itemID")
    fun getItemByItemID(itemID: String): List<Item>

    @Query("SELECT * FROM Item")
    fun getItems(): List<Item>

    @Query("SELECT * FROM Item ORDER BY CASE WHEN :isAsc = 1 THEN itemLength END ASC, CASE WHEN :isAsc = 0 THEN itemLength END DESC")
    fun getItemsSorted(isAsc: Boolean): List<Item>
}