package com.aos.myapplication.view.video

import androidx.lifecycle.ViewModel
import com.aos.domain.usecase.SearchVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val searchVideoUseCase: SearchVideoUseCase): ViewModel() {

}