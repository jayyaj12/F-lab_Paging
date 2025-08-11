package com.aos.myapplication.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aos.domain.entity.VideoEntityItem
import com.aos.domain.usecase.AddFavoriteVideoUseCase
import com.aos.domain.usecase.SearchVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@HiltViewModel
class SearchVideoViewModel @Inject constructor(private val searchVideoUseCase: SearchVideoUseCase, private val addFavoriteVideoUseCase: AddFavoriteVideoUseCase) : ViewModel() {

    // 검색어 저장
    var query = MutableLiveData("")

    // 검색 트리거
    private val _searchTrigger = MutableStateFlow(false)
    private val searchTrigger = _searchTrigger

    // 검색 결과 비어있는지 여부
    private var _isEmpty = MutableLiveData<Boolean>(false)
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    val pagedVideos = searchTrigger
        .filter { query.value != "" }
        .flatMapLatest {
            searchVideoUseCase(query.value!!)
        }.cachedIn(viewModelScope)

    // 비디오가 비어있는지 상태 저장
    fun isEmptyVideos(isEmpty: Boolean) {
        _isEmpty.value = isEmpty
    }

    // 검색 버튼 클릭
    fun onClickedSearchBtn() {
        // 비어 있지 않을때만 검색
        _searchTrigger.value = !searchTrigger.value
    }

    // 검색어 변경 감지
    fun onQueryTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        query.value = s.toString()
    }

    fun onClickedAddFavorite(video: VideoEntityItem) {
        viewModelScope.launch {
            addFavoriteVideoUseCase(video)
        }
    }
}