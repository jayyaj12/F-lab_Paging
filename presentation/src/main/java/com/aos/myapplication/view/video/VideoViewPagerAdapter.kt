package com.aos.myapplication.view.video

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aos.myapplication.view.video.favorite.FavoriteVideoFragment
import com.aos.myapplication.view.video.search.SearchVideoFragment

class VideoViewPagerAdapter(private val titles: List<String>, activity: MainActivity): FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchVideoFragment()
            1 -> FavoriteVideoFragment()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}