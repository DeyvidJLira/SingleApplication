package com.dvitsolutions.singleapplication

import android.content.Intent
import android.os.IBinder
import android.preference.PreferenceManager
import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.util.Log
import java.util.concurrent.TimeUnit


/**
 * Created by Deyvid on 28/09/2017.
 */

class SingleService : Service() {

    private var thread: Thread? = null
    private var context: Context? = null
    private var running = false

    override fun onDestroy() {
        running = false
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        running = true
        context = this

        // start a thread that periodically checks if your app is in the foreground
        thread = Thread(Runnable {
            do {
                handleSingleMode()
                try {
                    Thread.sleep(INTERVAL)
                } catch (e: InterruptedException) {
                    Log.i(TAG, "Thread interrupted: 'SingleService'")
                }

            } while (running)
            stopSelf()
        })

        thread!!.start()
        return Service.START_NOT_STICKY
    }

    private fun handleSingleMode() {
        if (isSingleModeActive(this)) {
            if (isInBackground) {
                restoreApp() // restore!
            }
        }
    }

    private val isInBackground: Boolean
        get() {
            val am = context!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

            val taskInfo = am.getRunningTasks(1)
            val componentInfo = taskInfo[0].topActivity
            return !context!!.getApplicationContext().getPackageName().equals(componentInfo.packageName)
        }

    private fun restoreApp() {
        // Restart activity
        val i = Intent(context, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(i)
    }

    fun isSingleModeActive(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(PREF_SINGLE_MODE, false)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {

        private val INTERVAL = TimeUnit.SECONDS.toMillis(2)
        private val TAG = SingleService::class.java.simpleName
        private val PREF_SINGLE_MODE = "pref_single_mode"

    }
}