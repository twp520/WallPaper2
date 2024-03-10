package com.flight.wallpaper2.additional

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.flight.wallpaper2.AppWow
import com.flight.wallpaper2.R


class PluginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {}
            })
        AppWow.instance.showAdDirectly(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        AppWow.instance.showAdDirectly(this)
    }


    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onDestroy() {
        super.onDestroy()
        val activityManager: ActivityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val tasks = activityManager.appTasks
        for (task in tasks) {
            task.finishAndRemoveTask()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }
}