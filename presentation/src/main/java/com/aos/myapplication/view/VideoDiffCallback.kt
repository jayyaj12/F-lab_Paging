package com.aos.myapplication.view

import androidx.recyclerview.widget.DiffUtil
import com.aos.domain.model.UiGetVideoModel
import com.aos.domain.model.Video

class VideoDiffCallback: DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(
        oldItem: Video,
        newItem: Video
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Video,
        newItem: Video
    ): Boolean {
        return oldItem == newItem
    }
}