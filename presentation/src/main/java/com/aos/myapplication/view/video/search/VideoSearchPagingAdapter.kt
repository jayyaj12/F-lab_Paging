package com.aos.myapplication.view.video.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aos.domain.entity.VideoEntity
import com.aos.myapplication.databinding.ItemVideoBinding

class VideoSearchPagingAdapter(private val clickedFavoriteBtn: (VideoEntity) -> Unit, private val clickedOpenDetailBtn: (Int) -> Unit): PagingDataAdapter<VideoEntity, VideoSearchPagingAdapter.VideoViewHolder>(
    diffCallback
) {

    inner class VideoViewHolder(private val binding: ItemVideoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoEntity) {
            with(binding) {
                video = item
                executePendingBindings()

                btnFavorite.setOnClickListener {
                    clickedFavoriteBtn(item)
                }

                clContainer.setOnClickListener {
                    clickedOpenDetailBtn(bindingAdapterPosition)
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
        private val diffCallback = object : DiffUtil.ItemCallback<VideoEntity>() {
            override fun areItemsTheSame(old: VideoEntity, new: VideoEntity) =
                old.id == new.id
            override fun areContentsTheSame(old: VideoEntity, new: VideoEntity) = old == new
        }
    }
}