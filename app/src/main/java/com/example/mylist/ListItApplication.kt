package com.example.mylist

import android.app.Application
import com.example.mylist.di.AppContainer

class ListItApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
