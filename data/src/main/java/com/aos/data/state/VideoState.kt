package com.aos.data.state

import com.aos.domain.model.VideoType

object VideoState {
    var lastType: VideoType = VideoType.TYPE_B // 마지막 타입 저장 변수
    var lastIndex = 0 // 마지막 타입이 몇번 추가되었는지 판단하는 변수

    // 새로 검색할 경우 값 초기화
    fun clearData() {
        lastType = VideoType.TYPE_B
        lastIndex = 0
    }
}