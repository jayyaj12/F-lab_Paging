package com.aos.data.mapper

import com.aos.data.entity.GetVideoEntity
import com.aos.data.state.VideoState
import com.aos.domain.model.Video
import com.aos.domain.model.VideoType
import com.aos.domain.model.UiGetVideoModel
import timber.log.Timber

fun com.aos.data.entity.Video.toDomain(type: VideoType): Video =
    Video(
        id = "${this.title}_${this.url}".hashCode().toString(),
        title = this.title,
        thumbnail = this.thumbnail,
        isType = type
    )

fun GetVideoEntity.toUiVideoModel(): UiGetVideoModel {
    // 이전 페이지에서 남은 Type 상태 불러오기
    val lastType = VideoState.lastType
    val lastIndex = VideoState.lastIndex

    // 남은 Type을 몇 개 더 채워야 3개가 채워지는지 계산
    val addPreviousType = if (lastIndex != 0) {
        3 - lastIndex
    } else {
        0
    }

    var typeSwitch = lastType // 현재 타입 (초기엔 이전 페이지의 마지막 타입)
    var typeCount = 0 // 현재 타입이 몇 번 반복되었는지

    val videoModel = UiGetVideoModel(
        videos = this.videos.mapIndexed { videoIndex, document ->
            if (videoIndex < addPreviousType) {
                // 이전 Type이 3개까지 채워지지 않았을 경우, 남은 수만큼 채움
                document.toDomain(lastType)
            } else {
                // 3개마다 Type 전환
                // 단, 이전 페이지에서 남은 개수가 있을 경우 index 보정
                val index = if (lastIndex != 0) {
                    (videoIndex + lastIndex) % 3
                } else {
                    videoIndex % 3
                }

                // index가 0이면 새로운 Type으로 전환할 타이밍
                if (index == 0) {
                    typeSwitch = if (typeSwitch == VideoType.TYPE_A) {
                        VideoType.TYPE_B
                    } else {
                        VideoType.TYPE_A
                    }
                    typeCount = 0
                }

                typeCount++
                document.toDomain(typeSwitch)
            }
        },
        isEnd = this.meta.isEnd
    )

    // 현재 페이지가 끝난 후 Type 상태 저장 → 다음 페이지에서도 이어서 타입 계산 가능
    VideoState.lastType = typeSwitch
    VideoState.lastIndex = typeCount
    return videoModel

}