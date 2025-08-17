package com.aos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aos.data.local.dao.VideoDao
import com.aos.data.local.entity.VideoRoomEntity

@Database(entities = [VideoRoomEntity::class], version = 1, exportSchema = false)
abstract class VideoDatabase: RoomDatabase() {
    abstract fun videoDao(): VideoDao
}