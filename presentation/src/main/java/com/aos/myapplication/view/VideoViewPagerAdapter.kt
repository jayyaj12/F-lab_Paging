package com.aos.myapplication.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aos.myapplication.view.favorite.FavoriteVideoFragment
import com.aos.myapplication.view.search.SearchVideoFragment

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