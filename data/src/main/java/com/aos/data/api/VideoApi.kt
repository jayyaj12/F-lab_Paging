package com.aos.data.api

import com.aos.data.BuildConfig
import com.aos.data.response.VideoResponse

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface VideoApi {

    @GET("vclip")
    suspend fun getVideo(
        @Header("Authorization") auth: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): VideoResponse

}