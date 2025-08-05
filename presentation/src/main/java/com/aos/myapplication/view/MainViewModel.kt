package com.aos.myapplication.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.aos.data.source.VideoPagingSource
import com.aos.data.state.VideoState
import com.aos.domain.model.UiGetVideoModel
import com.aos.domain.usecase.SearchVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MainViewModel @Inject constructor(private val searchVideoUseCase: SearchVideoUseCase): ViewModel() {

    var query = MutableLiveData("")

    private val _searchTrigger = MutableStateFlow(false)
    private val searchTrigger = _searchTrigger

    private var _isEmpty = MutableLiveData<Boolean>(false)
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    val pagedVideos = searchTrigger
        .flatMapLatest {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { VideoPagingSource(query.value ?: "", searchVideoUseCase)}
            ).flow
        }.cachedIn(viewModelScope)

    fun isEmptyVideos(isEmpty: Boolean) {
        _isEmpty.value = isEmpty
    }

    fun onClickedSearchBtn() {
        // 비어 있지 않을때만 검색
        VideoState.clearData()
        _searchTrigger.value = !searchTrigger.value
    }

    // 검색어 변경 감지
    fun onQueryTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        query.value = s.toString()
    }
}