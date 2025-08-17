package com.aos.domain.usecase

import androidx.paging.PagingData
import com.aos.domain.entity.VideoEntity
import com.aos.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoriteVideoUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    operator fun invoke(): Flow<PagingData<VideoEntity>> = videoRepository.observeVideo()
}