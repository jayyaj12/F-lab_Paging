package com.aos.domain.usecase

import com.aos.domain.entity.VideoEntity
import com.aos.domain.repository.VideoRepository
import javax.inject.Inject

class DeleteFavoriteVideoUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke(video: VideoEntity)= videoRepository.delete(video)
}