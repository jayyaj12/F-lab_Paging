package com.aos.myapplication.view.search

import androidx.recyclerview.widget.DiffUtil
import com.aos.domain.entity.VideoEntityItem

class VideoDiffCallback: DiffUtil.ItemCallback<VideoEntityItem>() {
    override fun areItemsTheSame(
        oldItem: VideoEntityItem,
        newItem: VideoEntityItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: VideoEntityItem,
        newItem: VideoEntityItem
    ): Boolean {
        return oldItem == newItem
    }
}