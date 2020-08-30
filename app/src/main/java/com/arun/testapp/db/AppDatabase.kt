package com.arun.testapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arun.testapp.db.dao.ItemDao
import com.arun.testapp.db.entity.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun itemDao(): ItemDao

    companion object {
        const val TAG = "AppDatabase"

        private const val DATABASE_NAME = "test_room_db"
        var dbInstance: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {
            synchronized(AppDatabase::class.java) {
                if (dbInstance == null) {
                    dbInstance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return dbInstance
        }

        fun destroyDatabase() {
            dbInstance = null
        }
    }
}