package com.aos.myapplication.view.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aos.domain.entity.VideoEntityItem
import com.aos.domain.entity.VideoLocalItem
import com.aos.domain.usecase.DeleteFavoriteVideoUseCase
import com.aos.domain.usecase.ObserveFavoriteVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteVideoViewModel @Inject constructor(
    private val observeFavoriteVideoUseCase: ObserveFavoriteVideoUseCase,
    private val deleteFavoriteVideoUseCase: DeleteFavoriteVideoUseCase
) : ViewModel() {

    val pagedVideos = observeFavoriteVideoUseCase()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily, // 또는 WhileSubscribed(5000)
            initialValue = PagingData.empty()
        )

    fun deleteFavoriteVideo(video: VideoLocalItem) {
        viewModelScope.launch {
            deleteFavoriteVideoUseCase(video)
        }
    }
}