@file:OptIn(InternalSerializationApi::class)
package com.aos.data.response

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    val meta: Meta,
    @SerialName("documents")
    val videos: List<VideoResponseItem>
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
data class VideoResponseItem(
    val title: String,
    val url: String,
    @SerialName("datetime")
    val dateTime: String,
    @SerialName("play_time")
    val playTime: Int,
    val thumbnail: String,
    val author: String
)