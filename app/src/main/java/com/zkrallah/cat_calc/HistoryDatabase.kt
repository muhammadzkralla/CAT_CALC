package com.zkrallah.cat_calc

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zkrallah.cat_calc.model.History


@Database(entities = [History::class], version = 2)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO

    companion object {
        private var instance: HistoryDatabase? = null

        @Synchronized
        fun getInstance(context: Context): HistoryDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java,
                    "eqn_database")
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }
}
