package com.flight.wallpaper2.additional

import android.os.Build
import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import java.util.Locale

object AnalysisUtil {

    const val EVENT_TRIGGER = "ad_trigger"
    const val EVENT_CALL_DIALOG = "call_out_dialog"
    const val EVENT_DIALOG_SHOWED = "dialog_showed"
    const val EVENT_AD_SHOWED = "ad_showed"
    const val EVENT_AD_CLOSED = "ad_closed"
    const val EVENT_AD_CLICKED = "ad_clicked"
    const val EVENT_AD_LOAD_FAIL = "ad_load_fail"


    fun logEvent(key: String, args: Bundle? = null) {
        val bundle = Bundle()
        runCatching {
            bundle.putString("name", Build.PRODUCT)
            bundle.putInt("android_sdk_int", Build.VERSION.SDK_INT)
            bundle.putString("country", Locale.getDefault().country)
            args?.let {
                bundle.putAll(it)
            }
        }
        Firebase.analytics.logEvent(key, bundle)
    }
}