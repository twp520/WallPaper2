package com.flight.wallpaper2.additional

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import androidx.core.content.edit
import com.anythink.core.api.ATAdInfo
import com.anythink.core.api.AdError
import com.anythink.interstitial.api.ATInterstitial
import com.anythink.interstitial.api.ATInterstitialListener
import com.flight.wallpaper2.AppWow
import java.text.SimpleDateFormat
import java.util.Date

class AdProvider(
    context: Context,
    val strategy: AdStrategy
) : ATInterstitialListener {

    private val whatLoadAd = 1
    private val logTag = "AdManager"
    private val keyDate = "keyData"
    private val keyShowCount = "keyShowCount"
    private val keyClickCount = "keyClickCount"

    private val preferences =
        context.getSharedPreferences("AdProvider", Context.MODE_PRIVATE).apply {
            val lastDay = getString(keyDate, "")
            val today = SimpleDateFormat.getDateInstance().format(Date())
            if (!TextUtils.equals(lastDay, today)) {
                edit {
                    putString(keyDate, today)
                    remove(keyShowCount)
                    remove(keyClickCount)
                }
            }
        }

    private val handler = Handler(Looper.getMainLooper(), Handler.Callback {
        if (it.what == whatLoadAd) {
            mInterstitialAd.load()
        }
        return@Callback true
    })

    private val mInterstitialAd: ATInterstitial = ATInterstitial(context, strategy.adId).apply {
        setAdListener(this@AdProvider)
        load()
    }


    fun checkShowAd(): Boolean {
        val showCount = preferences.getInt(keyShowCount, 0)
        val clickCount = preferences.getInt(keyClickCount, 0)
        if (showCount >= strategy.maxShowCount ||
            clickCount >= strategy.maxClickCount
        ) {
            Log.d(logTag, "requestShowAd: today exceed max count")
            return false
        }
        val ad = mInterstitialAd
        val adStatus = ad.checkAdStatus()
        return if (adStatus.isReady) {
            true
        } else if (adStatus.isLoading) {
            false
        } else {
            ad.load()
            false
        }
    }

    fun showAd(activity: Activity) {
        mInterstitialAd.show(activity)
    }

    override fun onInterstitialAdLoaded() {

    }

    override fun onInterstitialAdLoadFail(p0: AdError?) {
        Log.d(logTag, "onInterstitialAdLoadFail: ${p0?.fullErrorInfo}")
        AnalysisUtil.logEvent(AnalysisUtil.EVENT_AD_LOAD_FAIL, Bundle().apply {
            putString("code", p0?.code ?: "")
        })
        handler.removeMessages(whatLoadAd)
        handler.sendEmptyMessageDelayed(whatLoadAd, 10000)
    }

    override fun onInterstitialAdClicked(p0: ATAdInfo?) {
        Log.d(logTag, "onInterstitialAdClicked: ")
        AnalysisUtil.logEvent(AnalysisUtil.EVENT_AD_CLICKED)
        val lastClickCount = preferences.getInt(keyClickCount, 0)
        preferences.edit {
            putInt(keyClickCount, lastClickCount + 1)
        }
    }

    override fun onInterstitialAdShow(p0: ATAdInfo?) {
        Log.d(logTag, "onInterstitialAdShow: ")
        AnalysisUtil.logEvent(AnalysisUtil.EVENT_AD_SHOWED)
        mInterstitialAd.load()
        val lastShowCount = preferences.getInt(keyShowCount, 0)
        preferences.edit {
            putInt(keyShowCount, lastShowCount + 1)
        }
    }

    override fun onInterstitialAdClose(p0: ATAdInfo?) {
        Log.d(logTag, "onInterstitialAdClose: ")
        AnalysisUtil.logEvent(AnalysisUtil.EVENT_AD_CLOSED)
        AppWow.instance.finishAct()
    }

    override fun onInterstitialAdVideoStart(p0: ATAdInfo?) {
    }

    override fun onInterstitialAdVideoEnd(p0: ATAdInfo?) {
    }

    override fun onInterstitialAdVideoError(p0: AdError?) {
    }
}