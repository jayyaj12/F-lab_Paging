package com.aos.data.api

import com.aos.data.entity.GetVideoEntity
import com.aos.domain.util.NetworkState

import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {

    @GET("vclip")
    suspend fun getVideo(
        @Query("query") query: String
    ): NetworkState<GetVideoEntity>

}