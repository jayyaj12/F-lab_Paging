package com.aos.domain.usecase

import com.aos.domain.model.UiGetVideoModel
import com.aos.domain.repository.VideoRepository
import javax.inject.Inject

class SearchVideoUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int,
        size: Int
    ): Result<UiGetVideoModel> = videoRepository.getVideos(query, page, size)

}