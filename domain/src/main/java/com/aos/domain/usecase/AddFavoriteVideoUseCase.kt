package com.aos.domain.usecase

import androidx.paging.PagingData
import com.aos.domain.entity.VideoEntityItem
import com.aos.domain.entity.VideoLocalItem
import com.aos.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFavoriteVideoUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke(video: VideoEntityItem) = videoRepository.insertVideo(video)
}