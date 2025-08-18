package com.aos.domain.repository

import androidx.paging.PagingData
import com.aos.domain.entity.VideoEntity
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideosPager(query: String): Flow<PagingData<VideoEntity>>
    fun observeVideo(): Flow<PagingData<VideoEntity>>
    suspend fun insertVideo(video: VideoEntity)
    suspend fun insertListOfVideo(videos: List<VideoEntity>)
    suspend fun delete(video: VideoEntity)
    suspend fun deleteListOfVideo(videos: List<VideoEntity>)
}