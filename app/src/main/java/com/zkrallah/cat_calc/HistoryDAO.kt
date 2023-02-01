package com.zkrallah.cat_calc

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zkrallah.cat_calc.model.History
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface HistoryDAO {

    @Query("select * from eqn_database")
    fun getHistory(): Single<List<History?>?>?

    @Insert
    fun insertEqn(post: History?): Completable?
}