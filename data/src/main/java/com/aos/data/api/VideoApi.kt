package com.aos.data.api

import VideoResponse
import com.aos.data.BuildConfig

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface VideoApi {

    @GET("vclip")
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    suspend fun getVideo(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): VideoResponse

}