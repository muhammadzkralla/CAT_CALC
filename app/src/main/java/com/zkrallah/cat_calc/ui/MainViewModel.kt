package com.zkrallah.cat_calc.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.cat_calc.HistoryDatabase
import com.zkrallah.cat_calc.model.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val database = HistoryDatabase.getInstance()

    val allHistory: LiveData<List<History>> = database.historyDAO().getHistory()

    fun insert(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            database.historyDAO().insertEqn(history)
        }
    }
}