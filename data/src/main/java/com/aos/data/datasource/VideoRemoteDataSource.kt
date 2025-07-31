package com.aos.data.datasource

import com.aos.data.entity.GetVideoEntity
import com.aos.domain.util.NetworkState


interface VideoRemoteDataSource {
    suspend fun getVideo(query: String): NetworkState<GetVideoEntity>
}