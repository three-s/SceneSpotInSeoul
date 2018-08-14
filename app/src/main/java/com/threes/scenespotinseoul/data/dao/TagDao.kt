package com.threes.scenespotinseoul.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.threes.scenespotinseoul.data.model.Tag

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tag: Tag): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(tags: List<Tag>): List<Long>

    @Query("SELECT * FROM tags WHERE ROWID = :rowId")
    fun loadByRowId(rowId: Long): Tag

    @Query("SELECT * FROM tags WHERE name = :name")
    fun loadByExactlyName(name: String): List<Tag>

    @Query("SELECT * FROM tags WHERE name LIKE :name")
    fun loadBySimilarName(name: String): List<Tag>

    @Query("SELECT * FROM tags")
    fun loadAll(): List<Tag>
}