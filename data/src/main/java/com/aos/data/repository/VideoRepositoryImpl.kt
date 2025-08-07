package com.aos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aos.data.api.VideoApi
import com.aos.data.source.VideoPagingSource
import com.aos.domain.entity.VideoEntityItem
import com.aos.domain.repository.VideoRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class VideoRepositoryImpl @Inject constructor(private val videoApi: VideoApi): VideoRepository {
    override suspend fun getVideosPager(query: String): Flow<PagingData<VideoEntityItem>> {
            return Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { VideoPagingSource(query, videoApi) }
            ).flow
    }
}