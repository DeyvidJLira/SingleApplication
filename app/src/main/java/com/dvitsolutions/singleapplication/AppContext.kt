package com.dvitsolutions.singleapplication

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.PowerManager
import android.os.PowerManager.WakeLock

/**
 * Created by Deyvid on 28/09/2017.
 */

class AppContext : Application() {

    lateinit var instance: AppContext
    private lateinit var wakeLock: WakeLock
    lateinit var onScreenOffReceiver: OnScreenOffReceiver

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerSingleModeScreenOffReceiver()
        startSingleService()
    }

    private fun registerSingleModeScreenOffReceiver() {
        val intentFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        onScreenOffReceiver = OnScreenOffReceiver()
        registerReceiver(onScreenOffReceiver, intentFilter)
    }

    fun getWakeLock(): WakeLock {
        if(wakeLock == null) {
            val powerManager: PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakeup")
        }
        return wakeLock;
    }

    private fun startSingleService() {
        startService(Intent(this, SingleService::class.java))
    }
}