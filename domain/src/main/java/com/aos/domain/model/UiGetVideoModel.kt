package com.aos.domain.model

data class UiGetVideoModel(
    val documents: List<Document>,
    val isEnd: Boolean
)

data class Document(
    val id: String,
    val title: String,
    val thumbnail: String,
)