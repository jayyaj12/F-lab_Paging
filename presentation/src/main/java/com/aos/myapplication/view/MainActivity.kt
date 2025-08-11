package com.aos.myapplication.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.aos.myapplication.view.search.VideoPagingAdapter
import com.aos.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var videoPagingAdapter: VideoPagingAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.vm = mainViewModel
        setContentView(binding.root)

        setupVideoListAdapter()
        setupViewModelObserver()
    }

    private fun setupVideoListAdapter() {
        videoPagingAdapter = VideoPagingAdapter()
        videoPagingAdapter.addLoadStateListener { loadStates ->
            // 에러처리 
            val errorState = when {
                loadStates.refresh is LoadState.Error -> loadStates.refresh as LoadState.Error
                loadStates.prepend is LoadState.Error -> loadStates.prepend as LoadState.Error
                loadStates.append is LoadState.Error -> loadStates.append as LoadState.Error
                else -> null
            }

            errorState?.let {
                Timber.e("[PagingError]: ${it.error.message}")
                Toast.makeText(this@MainActivity, "에러 발생: ${it.error.message}", Toast.LENGTH_SHORT).show()
            }
            
            // 데이터 조회 시 빈 데이터 여부 확인
            val isEmpty = loadStates.refresh is LoadState.NotLoading &&
                    loadStates.append.endOfPaginationReached &&
                    videoPagingAdapter.itemCount == 0

            mainViewModel.isEmptyVideos(isEmpty)
        }
        with(binding.rvVideoList) {
            addItemDecoration(
                DividerItemDecoration(context, RecyclerView.VERTICAL)
            )
            adapter = videoPagingAdapter
        }
    }

    private fun setupViewModelObserver() {
        lifecycleScope.launch {
            mainViewModel.pagedVideos.collectLatest {
                videoPagingAdapter.submitData(it )
            }
        }
    }
}