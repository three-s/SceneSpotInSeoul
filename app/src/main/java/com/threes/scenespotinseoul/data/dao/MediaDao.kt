package com.threes.scenespotinseoul.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.threes.scenespotinseoul.data.model.Media

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(media: Media)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(media: List<Media>)

    @Transaction
    @Query("SELECT * FROM media WHERE id = :mediaId")
    fun loadById(mediaId: Int): Media

    @Transaction
    @Query("SELECT * FROM media")
    fun loadAll(): List<Media>
}