package com.aos.myapplication.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aos.myapplication.R
import com.aos.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var videoListAdapter: VideoListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.vm = mainViewModel
        setContentView(binding.root)

        setupVideoListAdapter()
        setupViewModelObserver()
    }

    private fun setupVideoListAdapter() {
        binding.rvVideoList.adapter = videoListAdapter
    }

    private fun setupViewModelObserver() {
        mainViewModel.getSearchVideo.observe(this) {
            Timber.e("it.documents ${it.documents}")
            videoListAdapter.submitList(it.documents)
        }
    }
}