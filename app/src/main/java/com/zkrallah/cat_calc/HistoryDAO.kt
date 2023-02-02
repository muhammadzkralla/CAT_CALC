package com.zkrallah.cat_calc

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zkrallah.cat_calc.model.History
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface HistoryDAO {

    @Query("select * from history_table")
    fun getHistory(): LiveData<List<History>>

    @Insert
    suspend fun insertEqn(post: History?)
}