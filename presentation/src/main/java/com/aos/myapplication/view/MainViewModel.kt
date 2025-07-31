package com.aos.myapplication.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.domain.model.UiGetVideoModel
import com.aos.domain.usecase.SearchVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MainViewModel @Inject constructor(private val searchVideoUseCase: SearchVideoUseCase): ViewModel() {

    var query = MutableLiveData<String>("")
    private var _getSearchVideo = MutableLiveData<UiGetVideoModel>()
    val getSearchVideo: LiveData<UiGetVideoModel> get() = _getSearchVideo

    private fun searchVideo(query: String) {
        viewModelScope.launch {
            searchVideoUseCase(query).onSuccess {
                Timber.d("[selectBook result] $it")
                _getSearchVideo.postValue(it)
            }.onFailure {
                Timber.e("[Error] ${it.message}")
            }
        }
    }

    fun onClickedSearchBtn() {
        // 비어 있지 않을때만 검색
        query.value?.let { query ->
            if(query.isNotBlank()) {
                searchVideo(query)
            }
        }

    }

    // 검색어 변경 감지
    fun onQueryTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        query.postValue(s.toString())
    }
}