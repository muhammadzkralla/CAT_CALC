package com.zkrallah.cat_calc

import android.app.Application

class CalcApp : Application() {

    companion object {
        lateinit var ctx: Application
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this
    }
}