package com.aos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aos.data.local.dao.VideoDao
import com.aos.data.local.entity.VideoEntity

@Database(entities = [VideoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun videoDao(): VideoDao
}