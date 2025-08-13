package com.aos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aos.data.api.VideoApi
import com.aos.data.local.dao.VideoDao
import com.aos.data.local.entity.VideoEntity
import com.aos.data.mapper.toVideoEntity
import com.aos.data.mapper.toVideoLocalItem
import com.aos.data.source.VideoPagingSource
import com.aos.domain.entity.VideoEntityItem
import com.aos.domain.entity.VideoLocalItem
import com.aos.domain.entity.VideoType
import com.aos.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoApi: VideoApi,
    private val videoDao: VideoDao
) : VideoRepository {
    override suspend fun getVideosPager(query: String): Flow<PagingData<VideoEntityItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { VideoPagingSource(query, videoApi) }
        ).flow
    }

    override fun observeVideo(): Flow<PagingData<VideoLocalItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { videoDao.observeVideos() }
        ).flow.map {
            it.map { entity ->
                entity.toVideoLocalItem()
            }
        }
    }

    override suspend fun insertVideo(video: VideoEntityItem) {
        videoDao.insert(video.toVideoEntity())
    }

    override suspend fun delete(video: VideoLocalItem) {
        videoDao.delete(video.id)
    }
}