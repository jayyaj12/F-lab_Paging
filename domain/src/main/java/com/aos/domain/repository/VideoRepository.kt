package com.aos.domain.repository

import androidx.paging.PagingData
import com.aos.domain.model.UiGetVideoModel
import com.aos.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideosPager(query: String): Flow<PagingData<Video>>
}