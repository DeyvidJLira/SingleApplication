package com.dvitsolutions.singleapplication

import android.preference.PreferenceManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context

/**
 * Created by Deyvid on 28/09/2017.
 */
class OnScreenOffReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_SCREEN_OFF == intent.action) {
            val context = context.getApplicationContext() as AppContext
            if (isSingleModeActive(context)) {
                wakeUpDevice(context)
            }
        }
    }

    private fun wakeUpDevice(context: AppContext) {
        val wakeLock = context.getWakeLock()
        if (wakeLock.isHeld()) {
            wakeLock.release()
        }
        wakeLock.acquire()
        wakeLock.release()
    }

    private fun isSingleModeActive(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(PREF_SINGLE_MODE, false)
    }

    companion object {
        private val PREF_SINGLE_MODE = "pref_single_mode"
    }
}