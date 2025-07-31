package com.aos.domain.usecase

import com.aos.domain.model.UiGetVideoModel
import com.aos.domain.repository.VideoRepository
import javax.inject.Inject

class SelectVideoUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {

    suspend operator fun invoke(
        query: String
    ): Result<UiGetVideoModel> = videoRepository.getVideo(query)

}