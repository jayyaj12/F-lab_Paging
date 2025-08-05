package com.aos.data.datasource

import com.aos.data.entity.GetVideoEntity
import com.aos.domain.util.NetworkState


interface VideoRemoteDataSource {
    suspend fun getVideos(query: String, page: Int, size: Int): NetworkState<GetVideoEntity>
}