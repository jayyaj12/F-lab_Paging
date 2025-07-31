package com.aos.domain.repository

import com.aos.domain.model.UiGetVideoModel

interface VideoRepository {
    suspend fun getVideo(query: String): Result<UiGetVideoModel>
}