package com.aos.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aos.data.local.entity.VideoRoomEntity

@Dao
interface VideoDao {
    @Query("SELECT * FROM video ORDER BY title ASC")
    fun observeVideos(): PagingSource<Int, VideoRoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: VideoRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<VideoRoomEntity>)

    @Query("DELETE FROM video WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM video WHERE id IN (:ids)")
    suspend fun delete(ids: List<String>)
}