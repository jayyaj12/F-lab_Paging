package com.aos.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val thumbnail: String
)
