package com.aos.data.state

import com.aos.domain.model.VideoType

object VideoState {
    var lastType: VideoType = VideoType.TYPE_B
    var lastIndex = 0

    fun clearData() {
        lastType = VideoType.TYPE_B
        lastIndex = 0
    }
}