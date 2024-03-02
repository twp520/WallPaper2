package com.flight.wallpaper2.additional

data class AdStrategy(
    val adId: String,
    val maxShowCount: Int,
    val maxClickCount: Int,
    val triggerAdInterval: Long
)