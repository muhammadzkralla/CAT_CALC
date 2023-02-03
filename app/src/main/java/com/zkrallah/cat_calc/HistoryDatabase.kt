package com.zkrallah.cat_calc


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zkrallah.cat_calc.CalcApp.Companion.ctx
import com.zkrallah.cat_calc.model.History


@Database(entities = [History::class], version = 2)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO

    companion object {
        private var instance: HistoryDatabase? = null
        private val context = ctx

        @Synchronized
        fun getInstance(): HistoryDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java,
                    "eqn_database")
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }
}
