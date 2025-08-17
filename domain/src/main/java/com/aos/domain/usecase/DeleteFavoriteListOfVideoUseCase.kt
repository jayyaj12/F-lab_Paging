package com.aos.domain.usecase

import android.util.Log
import com.aos.domain.entity.VideoEntity
import com.aos.domain.repository.VideoRepository
import javax.inject.Inject

class DeleteFavoriteListOfVideoUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke(videos: List<VideoEntity>){
        Log.e("tewrwrew","asdasd")
        videoRepository.deleteListOfVideo(videos)
    }
}