package com.example.launchertestapp

import android.app.Application
import android.content.Context



class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        PluginManager.getInstance(base).init()
    }
}