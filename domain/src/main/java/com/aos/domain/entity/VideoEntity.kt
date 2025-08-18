package com.aos.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class VideoEntity(
    val id: String, // 고유 id
    val title: String, // 제목
    val thumbnail: String, // 비디오 썸네일
    val type: VideoType = VideoType.TYPE_A, // 비디오 타입 A, B
    var isLast: Boolean = false, // 마지막 비디오면 마지막 표시
    var isFavorite: Boolean = false // 즐겨찾기 여부
) : Parcelable


enum class VideoType {
    TYPE_A, TYPE_B
}