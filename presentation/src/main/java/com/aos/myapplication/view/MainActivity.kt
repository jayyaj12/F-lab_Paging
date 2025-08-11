package com.aos.myapplication.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aos.myapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var videoViewPagerAdapter: VideoViewPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setupTabLayout()
    }

    private fun setupTabLayout() {
        val tabTitles = listOf("검색", "즐겨찾기")

        with(binding) {
            videoViewPagerAdapter = VideoViewPagerAdapter(titles = tabTitles, activity = this@MainActivity)

            videoViewPager.adapter = videoViewPagerAdapter

            TabLayoutMediator(tabLayout, videoViewPager) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }
}