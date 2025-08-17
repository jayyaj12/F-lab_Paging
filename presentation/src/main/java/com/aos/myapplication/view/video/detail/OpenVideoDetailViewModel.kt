package com.aos.myapplication.view.video.detail

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.domain.entity.VideoEntity
import com.aos.domain.usecase.AddFavoriteListOfVideoUseCase
import com.aos.domain.usecase.AddFavoriteVideoUseCase
import com.aos.domain.usecase.DeleteFavoriteListOfVideoUseCase
import com.aos.domain.usecase.DeleteFavoriteVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OpenVideoDetailViewModel @Inject constructor(
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

    fun addFavoriteListOfVideo(videos: List<VideoEntity>) {
        viewModelScope.launch {
            addFavoriteListOfVideoUseCase(videos)
        }
    }

    fun deleteFavoriteListOfVideo(videos: List<VideoEntity>) {
        viewModelScope.launch {
            deleteFavoriteListOfVideoUseCase(videos)
        }
    }

    fun saveVideosState() {
        val favoriteVideos = mutableListOf<VideoEntity>()
        val nonFavoriteVideos = mutableListOf<VideoEntity>()

        if(initVideos == videos) return

        videos.forEach { video ->
            if (video.isFavorite) {
                favoriteVideos.add(video)
            } else {
                nonFavoriteVideos.add(video)
            }
        }

        if (favoriteVideos.isNotEmpty()) {
            Timber.e("favoriteVideos $favoriteVideos")
            addFavoriteListOfVideo(favoriteVideos)
        }
        if (nonFavoriteVideos.isNotEmpty()) {
            Timber.e("nonFavoriteVideos $nonFavoriteVideos")
            deleteFavoriteListOfVideo(nonFavoriteVideos)
        }
    }
}
