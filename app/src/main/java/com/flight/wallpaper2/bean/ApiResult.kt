package com.flight.wallpaper2.bean


data class ApiResult<T>(
    val msg: String,
    val code: Int,
    val res: T?
) {

    fun isSuccess() = code == 0 && res != null
}