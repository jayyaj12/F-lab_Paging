package com.aos.myapplication.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.aos.myapplication.adapter.VideoPagingAdapter
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