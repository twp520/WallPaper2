package com.flight.wallpaper2.additional

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.flight.wallpaper2.AppWow


open class EventReceiver() : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null)
            return
        Log.d("EventReceiver", "onReceive: ${intent.action}")
        runCatching {
            AppWow.instance.reqShowAd()
        }
    }
}