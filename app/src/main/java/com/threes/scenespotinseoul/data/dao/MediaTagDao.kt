package com.threes.scenespotinseoul.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.threes.scenespotinseoul.data.model.MediaTag

@Dao
interface MediaTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mediaTag: MediaTag): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(mediaTags: List<MediaTag>): List<Long>

    @Query("SELECT * FROM media_tags WHERE mediaId = :mediaId")
    fun loadByMediaId(mediaId: Int): List<MediaTag>

    @Query("SELECT * FROM media_tags WHERE tagId = :tagId")
    fun loadByTagId(tagId: Int): List<MediaTag>
}