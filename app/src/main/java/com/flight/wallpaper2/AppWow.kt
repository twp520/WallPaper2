package com.flight.wallpaper2

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.webkit.WebView
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.BuildConfig
import com.adjust.sdk.LogLevel
import com.flight.wallpaper2.additional.Plugin
import com.flight.wallpaper2.additional.PluginActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus


class AppWow : Application() {

    companion object {
        lateinit var instance: AppWow
    }

    lateinit var preferences: SharedPreferences
    private val topScope = MainScope() + SupervisorJob()
    private lateinit var plugin: Plugin
    private var pluginActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        preferences = getSharedPreferences("WallWow", Context.MODE_PRIVATE)

        if (!isMainProcess(this))
            return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = getProcessName()
            if (!packageName.equals(processName)) {
                WebView.setDataDirectorySuffix(processName)
            }
        }

        Firebase.initialize(this)
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        Firebase.remoteConfig.fetchAndActivate()

        val config = AdjustConfig(
            this, getString(R.string.adjust_token),
            if (BuildConfig.DEBUG) AdjustConfig.ENVIRONMENT_SANDBOX else AdjustConfig.ENVIRONMENT_PRODUCTION
        )
        config.setLogLevel(LogLevel.SUPRESS)
        Adjust.onCreate(config)


        plugin = Plugin(this, topScope)
        plugin.init()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is PluginActivity) {
                    pluginActivity = activity
                }
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                Adjust.onResume()
            }

            override fun onActivityPaused(activity: Activity) {
                Adjust.onPause()
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })

    }

    private fun isMainProcess(context: Context?): Boolean {
        try {
            if (null != context) {
                return context.packageName == getProcessName(context)
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun getProcessName(cxt: Context): String? {
        val pid = Process.myPid()
        val am = cxt.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (procInfo in runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName
            }
        }
        return null
    }

    fun reqShowAd() {
        plugin.req()
    }

    fun showAdDirectly(activity: Activity) {
        plugin.show(activity)
    }

    fun finishAct() {
        pluginActivity?.finish()
        pluginActivity = null
    }
}