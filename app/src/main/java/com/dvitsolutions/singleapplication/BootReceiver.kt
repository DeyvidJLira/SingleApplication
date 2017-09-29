package com.dvitsolutions.singleapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Deyvid on 28/09/2017.
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val intent: Intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(intent)
    }
}