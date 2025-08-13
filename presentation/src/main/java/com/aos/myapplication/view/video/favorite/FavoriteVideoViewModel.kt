package com.aos.myapplication.view.video.favorite

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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class FavoriteVideoViewModel @Inject constructor(
    private val observeFavoriteVideoUseCase: ObserveFavoriteVideoUseCase,
    private val deleteFavoriteVideoUseCase: DeleteFavoriteVideoUseCase
) : ViewModel() {

    private val _eventMessage = MutableSharedFlow<String>()
    val eventMessage: SharedFlow<String> = _eventMessage

    val pagedVideos = observeFavoriteVideoUseCase()
        .cachedIn(viewModelScope)

    fun deleteFavoriteVideo(video: VideoLocalItem) {
        viewModelScope.launch {
            runCatching {
                deleteFavoriteVideoUseCase(video)
            }.onSuccess {
                Timber.e("test")
                _eventMessage.emit("즐겨찾기가 삭제되었습니다.")
            }.onFailure {
                // Complete the code between the fim markers
                _eventMessage.emit("즐겨찾기 삭제 실패하였습니다.")
            }
        }
    }
}