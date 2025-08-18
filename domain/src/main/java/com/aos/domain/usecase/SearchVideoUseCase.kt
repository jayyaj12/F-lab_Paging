package com.aos.domain.usecase

import androidx.paging.PagingData
import com.aos.domain.entity.VideoEntity
import com.aos.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchVideoUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke(
        query: String
    ): Flow<PagingData<VideoEntity>> = videoRepository.getVideosPager(query)
}