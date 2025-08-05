package com.aos.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aos.domain.model.Video
import com.aos.myapplication.databinding.ItemVideoBinding
import com.aos.myapplication.view.VideoDiffCallback

class VideoPagingAdapter: PagingDataAdapter<Video, VideoPagingAdapter.VideoViewHolder>(
    VideoDiffCallback()
) {

    inner class VideoViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Video) {
            binding.video = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}