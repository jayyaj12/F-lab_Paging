package com.aos.data.mapper

import com.aos.data.entity.GetVideoEntity
import com.aos.domain.model.Document
import com.aos.domain.model.UiGetVideoModel

fun com.aos.data.entity.Document.toDomain(): Document =
    Document(
        id = "${this.title}_${this.url}".hashCode().toString(),
        title = this.title,
        thumbnail = this.thumbnail
    )

fun GetVideoEntity.toUiVideoModel(): UiGetVideoModel {
    return UiGetVideoModel(
        documents = this.documents.map {
            it.toDomain()
        },
        isEnd = this.meta.isEnd
    )
}