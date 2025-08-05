@file:OptIn(kotlinx.serialization.InternalSerializationApi::class)
package com.aos.data.entity

import android.text.SpannedString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetVideoEntity(
    val meta: Meta,
    @SerialName("documents")
    val videos: List<Video>
)

@Serializable
data class Meta(
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("pageable_count")
    val pageableCount: Int,
    @SerialName("is_end")
    val isEnd: Boolean
)

@Serializable
data class Video(
    val title: String,
    val url: String,
    @SerialName("datetime")
    val dateTime: String,
    @SerialName("play_time")
    val playTime: Int,
    val thumbnail: String,
    val author: String
)