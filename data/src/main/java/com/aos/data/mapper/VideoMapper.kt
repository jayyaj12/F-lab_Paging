package com.aos.data.mapper

import com.aos.data.entity.GetVideoEntity
import com.aos.domain.model.Video
import com.aos.domain.model.VideoType
import com.aos.domain.model.UiGetVideoModel
import timber.log.Timber
// 매핑 결과를 담을 데이터 클래스
data class VideoPageMappingResult(
    val videos: List<Video>,
    val nextStartingType: VideoType,
    val nextStartingIndex: Int
)

fun com.aos.data.entity.Video.toDomain(type: VideoType): Video =
    Video(
        id = "${this.title}_${this.url}".hashCode().toString(),
        title = this.title,
        thumbnail = this.thumbnail,
        isType = type
    )

fun GetVideoEntity.toVideoModel(initialType: VideoType, initialIndex: Int): VideoPageMappingResult {
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
        videos = domainVideos,
        nextStartingType = typeForThisPage,
        nextStartingIndex = typeCount
    )
}