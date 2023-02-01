package com.zkrallah.cat_calc

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zkrallah.cat_calc.model.History


@Database(entities = [History::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun userDao(): HistoryDAO
    private var instance: com.zkrallah.cat_calc.RoomDatabase? = null

    @Synchronized
    open fun getInstance(context: Context): com.zkrallah.cat_calc.RoomDatabase? {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                com.zkrallah.cat_calc.RoomDatabase::class.java, "eqn_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return instance
    }
}