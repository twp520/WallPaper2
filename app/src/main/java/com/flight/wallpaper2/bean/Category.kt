package com.flight.wallpaper2.bean

import com.google.gson.annotations.SerializedName


data class Category(
    @SerializedName("ename")
    val title: String,
    val cover: String,
    val type: Int,
    val id: String
)