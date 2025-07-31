package com.aos.myapplication.view

import androidx.recyclerview.widget.DiffUtil
import com.aos.domain.model.Document
import com.aos.domain.model.UiGetVideoModel

class VideoDiffCallback: DiffUtil.ItemCallback<Document>() {
    override fun areItemsTheSame(
        oldItem: Document,
        newItem: Document
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Document,
        newItem: Document
    ): Boolean {
        return oldItem == newItem
    }
}