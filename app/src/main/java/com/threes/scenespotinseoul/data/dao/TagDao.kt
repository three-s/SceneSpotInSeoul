package com.threes.scenespotinseoul.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    fun loadByName(name: String): Tag?

    @Query("SELECT * FROM tags WHERE name LIKE :name")
    fun loadBySimilarName(name: String): List<Tag>

    @Query("SELECT * FROM tags")
    fun loadAll(): List<Tag>
}