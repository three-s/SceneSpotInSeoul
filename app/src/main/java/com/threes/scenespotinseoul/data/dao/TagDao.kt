package com.threes.scenespotinseoul.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.threes.scenespotinseoul.data.model.Tag

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tag: Tag): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tags: List<Tag>): List<Long>

    @Query("SELECT * FROM tags WHERE name = :name")
    fun loadByExactlyName(name: String): Tag

    @Query("SELECT * FROM tags WHERE name LIKE :name ORDER BY LENGTH(name) ASC")
    fun loadBySimilarName(name: String): List<Tag>

    @Query("SELECT * FROM tags WHERE name LIKE :name ORDER BY LENGTH(name) ASC LIMIT :limit OFFSET :offset")
    fun loadBySimilarName(name: String, limit: Int, offset: Int = 0): List<Tag>

    @Query("SELECT * FROM tags")
    fun loadAll(): List<Tag>

    @Query("SELECT * FROM tags LIMIT :limit OFFSET :offset")
    fun loadAll(limit: Int, offset: Int = 0): List<Tag>
}