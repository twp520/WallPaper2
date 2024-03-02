package com.flight.wallpaper2.additional

import android.content.Context
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.flight.wallpaper2.data.appPreference
import com.flight.wallpaper2.data.keyEnable
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume


object InstallManager {

    private var conditions = listOf("fb4a", "gclid", "not%20set", "", "youtubeads", "bytedance")
    private var retryCount = 0

    suspend fun shouldRun(context: Context): Boolean {
        return withTimeoutOrNull(30000) {
            if (appPreference.contains(keyEnable))
                return@withTimeoutOrNull appPreference.getBoolean(keyEnable, false)
            return@withTimeoutOrNull suspendCancellableCoroutine {
                val referrerClient = InstallReferrerClient.newBuilder(context).build()
                referrerClient.startConnection(object : InstallReferrerStateListener {

                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                        when (responseCode) {
                            InstallReferrerClient.InstallReferrerResponse.OK -> {
                                // Connection established.
                                val response = referrerClient.installReferrer
                                val referrerUrl = response.installReferrer
                                val enableSdk =
                                    conditions.stream()
                                        .anyMatch { str -> referrerUrl.contains(str) }
                                appPreference.edit().putBoolean(keyEnable, enableSdk).apply()
                                it.resume(enableSdk)
                            }

                            InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                                // API not available on the current Play Store app.
                                it.resume(true)
                            }

                            InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                                // Connection couldn't be established.
                                it.resume(true)
                            }
                        }

                    }

                    override fun onInstallReferrerServiceDisconnected() {
                        if (retryCount < 5) {
                            retryCount += 1
                            referrerClient.startConnection(this)
                        } else {
                            it.resume(true)
                        }
                    }

                })
            }
        } ?: false
    }
}