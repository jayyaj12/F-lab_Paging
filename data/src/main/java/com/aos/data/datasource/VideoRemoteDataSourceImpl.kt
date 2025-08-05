package com.aos.data.datasource

import com.aos.data.api.VideoService
import com.aos.data.entity.GetVideoEntity
import com.aos.domain.util.NetworkState
import jakarta.inject.Inject

class VideoRemoteDataSourceImpl @Inject constructor(private val videoService: VideoService): VideoRemoteDataSource {
    override suspend fun getVideos(
        query: String,
        page: Int,
        size: Int
    ): NetworkState<GetVideoEntity> {
        return videoService.getVideo(query, page, size)
    }
}