package com.dvitsolutions.singleapplication

import android.app.Application
import android.content.Intent

/**
 * Created by Deyvid on 28/09/2017.
 */

class AppContext : Application() {

    lateinit var instance: AppContext

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun startSingleService() {
        startService(Intent(this, SingleService::class.java))
    }
}