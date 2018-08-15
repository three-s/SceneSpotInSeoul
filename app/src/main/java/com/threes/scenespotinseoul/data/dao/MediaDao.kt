package com.threes.scenespotinseoul.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.threes.scenespotinseoul.data.model.Media

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(media: Media): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(media: List<Media>): List<Long>

    @Query("SELECT * FROM media WHERE ROWID = :rowId")
    fun loadByRowId(rowId: Long): Media

    @Query("SELECT * FROM media WHERE id = :mediaId")
    fun loadById(mediaId: Int): Media

    @Query("SELECT * FROM media WHERE name = :name")
    fun loadByName(name: String): Media

    @Query("SELECT * FROM media")
    fun loadAll(): List<Media>
}