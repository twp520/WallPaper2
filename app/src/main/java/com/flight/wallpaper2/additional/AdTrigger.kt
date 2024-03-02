package com.flight.wallpaper2.additional

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import kotlinx.coroutines.flow.MutableSharedFlow


class AdTrigger(
    private val context: Context
) {

    private val batteryActionList = listOf(
        Intent.ACTION_BATTERY_LOW,
        Intent.ACTION_BATTERY_CHANGED,
        Intent.ACTION_BATTERY_OKAY,
        Intent.ACTION_POWER_CONNECTED,
        Intent.ACTION_POWER_DISCONNECTED,
    )

    private val packageActionList = listOf(
        Intent.ACTION_PACKAGE_ADDED,
        Intent.ACTION_PACKAGE_REMOVED,
        Intent.ACTION_PACKAGE_REPLACED,
    )

    fun start() {

        val intentFilter = IntentFilter()
        batteryActionList.forEach {
            intentFilter.addAction(it)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        }
        runCatching {
            context.registerReceiver(BatteryReceiver(), intentFilter)
        }

        val packageFilter = IntentFilter()
        packageActionList.forEach {
            packageFilter.addAction(it)
        }
        packageFilter.addDataScheme("package")
        context.registerReceiver(PackageReceiver(), packageFilter)
    }


}