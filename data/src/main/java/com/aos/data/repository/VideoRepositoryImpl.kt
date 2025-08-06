package com.aos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aos.data.datasource.VideoRemoteDataSourceImpl
import com.aos.data.mapper.toUiVideoModel
import com.aos.data.source.VideoPagingSource
import com.aos.data.util.RetrofitFailureStateException
import com.aos.domain.model.UiGetVideoModel
import com.aos.domain.model.Video
import com.aos.domain.repository.VideoRepository
import com.aos.domain.util.NetworkState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VideoRepositoryImpl @Inject constructor(private val videoRemoteDataSourceImpl: VideoRemoteDataSourceImpl): VideoRepository {
    override suspend fun getVideosPager(query: String): Flow<PagingData<Video>> {
            return Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { VideoPagingSource(query, videoRemoteDataSourceImpl) }
            ).flow
    }
}