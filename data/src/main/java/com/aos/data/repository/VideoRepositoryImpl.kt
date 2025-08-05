package com.aos.data.repository

import com.aos.data.datasource.VideoRemoteDataSourceImpl
import com.aos.data.mapper.toUiVideoModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.domain.model.UiGetVideoModel
import com.aos.domain.repository.VideoRepository
import com.aos.domain.util.NetworkState
import jakarta.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val videoRemoteDataSourceImpl: VideoRemoteDataSourceImpl): VideoRepository {
    override suspend fun getVideos(query: String, page: Int, size: Int): Result<UiGetVideoModel> {
        return when(val data = videoRemoteDataSourceImpl.getVideos(query, page, size)) {
            is NetworkState.Success -> Result.success(data.body.toUiVideoModel())
            is NetworkState.Failure -> Result.failure(
                RetrofitFailureStateException(
                    data.error, data.code
                )
            )
            is NetworkState.NetworkError -> Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> Result.failure(IllegalStateException("unknownError"))
        }
    }
}