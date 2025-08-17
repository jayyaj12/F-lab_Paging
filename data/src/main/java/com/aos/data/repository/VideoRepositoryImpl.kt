package com.aos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aos.data.api.VideoApi
import com.aos.data.local.dao.VideoDao
import com.aos.data.mapper.toVideoEntity
import com.aos.data.mapper.toVideoRoomEntity
import com.aos.data.source.VideoPagingSource
import com.aos.domain.entity.VideoEntity
import com.aos.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoApi: VideoApi,
    private val videoDao: VideoDao
) : VideoRepository {
    override suspend fun getVideosPager(query: String): Flow<PagingData<VideoEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { VideoPagingSource(query, videoApi) }
        ).flow
    }

    override fun observeVideo(): Flow<PagingData<VideoEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { videoDao.observeVideos() }
        ).flow.map {
            it.map { entity ->
                entity.toVideoEntity()
            }
        }
    }

    override suspend fun insertVideo(video: VideoEntity) {
        videoDao.insert(video.toVideoRoomEntity())
    }

    override suspend fun delete(video: VideoEntity) {
        videoDao.delete(video.id)
    }
}