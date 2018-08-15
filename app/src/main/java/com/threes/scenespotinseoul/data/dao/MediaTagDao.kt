package com.threes.scenespotinseoul.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.threes.scenespotinseoul.data.model.MediaTag

@Dao
interface MediaTagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(mediaTag: MediaTag): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(mediaTags: List<MediaTag>): List<Long>

    @Query("SELECT * FROM media_tags WHERE mediaId = :mediaId")
    fun loadByMediaId(mediaId: Int): List<MediaTag>

    @Query("SELECT * FROM media_tags WHERE tagId = :tagId")
    fun loadByTagId(tagId: Int): List<MediaTag>
}