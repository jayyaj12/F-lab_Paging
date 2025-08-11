package com.aos.myapplication.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aos.domain.entity.VideoEntityItem
import com.aos.domain.entity.VideoLocalItem
import com.aos.myapplication.databinding.ItemVideoBinding
import com.aos.myapplication.databinding.ItemVideoFavoriteBinding
import timber.log.Timber

class VideoFavoritePagingAdapter(private val favoriteBtnText: String, private val clickedFavoriteBtn: (VideoLocalItem) -> Unit): PagingDataAdapter<VideoLocalItem, VideoFavoritePagingAdapter.VideoViewHolder>(
    diffCallback
) {

    inner class VideoViewHolder(private val binding: ItemVideoFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoLocalItem) {
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
        val binding = ItemVideoFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<VideoLocalItem>() {
            override fun areItemsTheSame(old: VideoLocalItem, new: VideoLocalItem) =
                old.id == new.id
            override fun areContentsTheSame(old: VideoLocalItem, new: VideoLocalItem) = old == new
        }
    }
}