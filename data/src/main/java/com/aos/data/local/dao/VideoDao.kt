package com.aos.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aos.data.local.entity.VideoEntity
import com.aos.domain.entity.VideoEntityItem
import com.aos.domain.entity.VideoLocalItem
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM video ORDER BY title ASC")
    fun observeVideos(): PagingSource<Int, VideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: VideoEntity)

    @Query("DELETE FROM video WHERE id = :id")
    suspend fun delete(id: String)
}