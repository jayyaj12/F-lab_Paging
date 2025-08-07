package com.aos.domain.entity

data class VideoEntity(
    val videoEntityItems: List<VideoEntityItem>,
    val isEnd: Boolean
)

data class VideoEntityItem(
    val id: String, // 고유 id
    val title: String, // 제목
    val thumbnail: String, // 비디오 썸네일
    val isType: VideoType, // 비디오 타입 A, B
    var isLast: Boolean = false // 마지막 비디오면 마지막 표시
)

enum class VideoType {
    TYPE_A, TYPE_B
}