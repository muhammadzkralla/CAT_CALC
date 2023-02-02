package com.zkrallah.cat_calc.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table" )
class History(
    @ColumnInfo(name = "eqn") val eq: String?,
){
    @PrimaryKey(autoGenerate = true) var id = 0
}
