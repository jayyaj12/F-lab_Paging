package com.aos.myapplication.view.video.detail

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.domain.entity.VideoEntity
import com.aos.domain.usecase.AddFavoriteListOfVideoUseCase
import com.aos.domain.usecase.DeleteFavoriteListOfVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    private val addFavoriteListOfVideoUseCase: AddFavoriteListOfVideoUseCase,
    private val deleteFavoriteListOfVideoUseCase: DeleteFavoriteListOfVideoUseCase
) : ViewModel() {
    lateinit var initVideos: List<VideoEntity>

    private val _videos = mutableStateListOf<VideoEntity>()
    val videos: List<VideoEntity> get() = _videos

    fun setVideos(list: List<VideoEntity>) {
        _videos.clear()
        _videos.addAll(list)

        initVideos(list)
    }

    fun initVideos(list: List<VideoEntity>) {
        initVideos = list
    }

    fun toggleFavorite(position: Int) {
        _videos[position] = _videos[position].copy(isFavorite = !_videos[position].isFavorite)
    }

    fun saveVideosState() {
        viewModelScope.launch {
            runCatching {
                val favoriteVideos = mutableListOf<VideoEntity>()
                val nonFavoriteVideos = mutableListOf<VideoEntity>()

                videos.forEach { currentVideo ->
                    val originalVideo = initVideos.find { it.id == currentVideo.id }

                    if (originalVideo != null && originalVideo.isFavorite != currentVideo.isFavorite) {
                        if (currentVideo.isFavorite) {
                            favoriteVideos.add(currentVideo)
                        } else {
                            nonFavoriteVideos.add(currentVideo)
                        }
                    }
                }

                // 변경된 항목이 있을 때만 API 호출
                if (favoriteVideos.isNotEmpty()) {
                    addFavoriteListOfVideoUseCase(favoriteVideos)
                }
                if (nonFavoriteVideos.isNotEmpty()) {
                    deleteFavoriteListOfVideoUseCase(nonFavoriteVideos)
                }
            }.onFailure {
                Timber.e("error: ${it.message}")
            }
        }
    }
}
