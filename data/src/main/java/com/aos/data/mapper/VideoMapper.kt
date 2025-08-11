package com.aos.data.mapper

import VideoResponse
import VideoResponseItem
import com.aos.data.local.entity.VideoEntity
import com.aos.domain.entity.VideoEntityItem
import com.aos.domain.entity.VideoLocalItem
import com.aos.domain.entity.VideoType

// 매핑 결과를 담을 데이터 클래스
data class VideoPageMappingResult(
    val videoEntityItems: List<VideoEntityItem>,
    val nextStartingType: VideoType,
    val nextStartingIndex: Int
)

fun VideoResponseItem.toDomain(type: VideoType): VideoEntityItem =
    VideoEntityItem(
        id = "${this.title}_${this.url}".hashCode().toString(),
        title = this.title,
        thumbnail = this.thumbnail,
        isType = type
    )

fun VideoResponse.toVideoModel(initialType: VideoType, initialIndex: Int): VideoPageMappingResult {
    var typeForThisPage = initialType
    var indexForThisPage = initialIndex

    val addPreviousType = if (indexForThisPage != 0) {
        3 - indexForThisPage
    } else {
        0
    }

    var typeCount = 0
    val domainVideos = this.videos.mapIndexed { videoIndex, video ->
        if (videoIndex >= addPreviousType) {
            val adjustedIndex = if (indexForThisPage != 0) {
                (videoIndex + indexForThisPage) % 3
            } else {
                videoIndex % 3
            }

            if (adjustedIndex == 0) {
                typeForThisPage =
                    if (typeForThisPage == VideoType.TYPE_A) VideoType.TYPE_B else VideoType.TYPE_A
                typeCount = 0
            }

            typeCount++
        }
        video.toDomain(typeForThisPage)
    }

    return VideoPageMappingResult(
        videoEntityItems = domainVideos,
        nextStartingType = typeForThisPage,
        nextStartingIndex = typeCount
    )
}

fun VideoEntity.toVideoLocalItem(): VideoLocalItem {
    return VideoLocalItem(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail
    )
}

fun VideoEntityItem.toVideoEntity(): VideoEntity {
    return VideoEntity(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail
    )
}