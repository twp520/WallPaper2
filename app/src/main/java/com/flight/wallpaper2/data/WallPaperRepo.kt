package com.flight.wallpaper2.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object WallPaperRepo {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://service.picasso.adesk.com/v1/vertical/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val service: WallPaperService by lazy { retrofit.create() }


    suspend fun getCategory() = service.getCategory(false, 1)


    suspend fun getWallpaper(
        categoryId: String,
        lastCount: Int = 1
    ) = service.getWallpaperByCategory(categoryId, 20, lastCount)
}