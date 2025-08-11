package com.aos.domain.repository

import androidx.paging.PagingData
import com.aos.domain.entity.VideoEntity
import com.aos.domain.entity.VideoEntityItem
import com.aos.domain.entity.VideoLocalItem
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideosPager(query: String): Flow<PagingData<VideoEntityItem>>
    fun observeVideo(): Flow<PagingData<VideoLocalItem>>
    suspend fun insertVideo(video: VideoEntityItem)
    suspend fun delete(video: VideoLocalItem)
}