package com.flight.wallpaper2.additional

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.flight.wallpaper2.AppWow

class AdWork(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        runCatching {
            AppWow.instance.reqShowAd()
        }
        return Result.success()
    }
}