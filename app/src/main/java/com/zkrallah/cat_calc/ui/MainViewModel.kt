package com.zkrallah.cat_calc.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.zkrallah.cat_calc.HistoryDatabase
import com.zkrallah.cat_calc.model.History
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = HistoryDatabase.getInstance(application)


    val allHistory: LiveData<List<History>> = database.historyDAO().getHistory()

    suspend fun insert(history: History) {
        database.historyDAO().insertEqn(history)
    }
}