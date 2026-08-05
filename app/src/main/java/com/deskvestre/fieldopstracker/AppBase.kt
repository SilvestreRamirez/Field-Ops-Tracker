package com.deskvestre.fieldopstracker

import android.app.Application

class AppBase : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}