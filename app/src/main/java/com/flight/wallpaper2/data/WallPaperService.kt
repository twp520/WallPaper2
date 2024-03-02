package com.flight.wallpaper2.data

import com.flight.wallpaper2.bean.ApiResult
import com.flight.wallpaper2.bean.CategoryWrapper
import com.flight.wallpaper2.bean.WallpaperWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WallPaperService {

    @GET("category")
    suspend fun getCategory(
        @Query("adult") adult: Boolean,
        @Query("first") first: Int
    ): ApiResult<CategoryWrapper>


    @GET("category/{id}/vertical")
    suspend fun getWallpaperByCategory(
        @Path("id") id: String,
        @Query("limit") count: Int,
        @Query("first") first: Int
    ): ApiResult<WallpaperWrapper>
}