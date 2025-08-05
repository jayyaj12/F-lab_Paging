package com.aos.domain.model

data class UiGetVideoModel(
    val videos: List<Video>,
    val isEnd: Boolean
)

data class Video(
    val id: String,
    val title: String,
    val thumbnail: String,
    val isType: VideoType,
    var isLast: Boolean = false
)

enum class VideoType {
    TYPE_A, TYPE_B
}