package com.aos.domain.repository

import com.aos.domain.model.UiGetVideoModel

interface VideoRepository {
    suspend fun getVideos(query: String, page:Int, size: Int): Result<UiGetVideoModel>
}