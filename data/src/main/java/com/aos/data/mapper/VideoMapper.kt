package com.aos.data.mapper

import com.aos.data.local.entity.VideoRoomEntity
import com.aos.data.response.VideoResponse
import com.aos.data.response.VideoResponseItem
import com.aos.domain.entity.VideoEntity
import com.aos.domain.entity.VideoType

// 매핑 결과를 담을 데이터 클래스
data class VideoPageMappingResult(
    val videoEntities: List<VideoEntity>,
    val nextStartingType: VideoType,
    val nextStartingIndex: Int
)

fun VideoResponseItem.toDomain(type: VideoType): VideoEntity =
    VideoEntity(
        id = "${this.title}_${this.url}".hashCode().toString(),
        title = this.title,
        thumbnail = this.thumbnail,
        type = type
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
        videoEntities = domainVideos,
        nextStartingType = typeForThisPage,
        nextStartingIndex = typeCount
    )
}

fun VideoEntity.toVideoRoomEntity(): VideoRoomEntity {
    return VideoRoomEntity(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail
    )
}

fun VideoRoomEntity.toVideoEntity(): VideoEntity {
    return VideoEntity(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail
    )
}