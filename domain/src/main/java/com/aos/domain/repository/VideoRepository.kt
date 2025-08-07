package com.aos.domain.repository

import androidx.paging.PagingData
import com.aos.domain.entity.VideoEntityItem
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideosPager(query: String): Flow<PagingData<VideoEntityItem>>
}