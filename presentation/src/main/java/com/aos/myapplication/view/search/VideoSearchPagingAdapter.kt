package com.aos.myapplication.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aos.domain.entity.VideoEntityItem
import com.aos.myapplication.databinding.ItemVideoBinding

class VideoSearchPagingAdapter(private val favoriteBtnText: String, private val clickedFavoriteBtn: (VideoEntityItem) -> Unit): PagingDataAdapter<VideoEntityItem, VideoSearchPagingAdapter.VideoViewHolder>(
    diffCallback
) {

    inner class VideoViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoEntityItem) {
            with(binding) {
                video = item
                executePendingBindings()

                btnFavorite.text = favoriteBtnText
                btnFavorite.setOnClickListener {
                    clickedFavoriteBtn(item)
                }
            }
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

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<VideoEntityItem>() {
            override fun areItemsTheSame(old: VideoEntityItem, new: VideoEntityItem) =
                old.id == new.id
            override fun areContentsTheSame(old: VideoEntityItem, new: VideoEntityItem) = old == new
        }
    }
}