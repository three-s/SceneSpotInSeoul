package com.threes.scenespotinseoul.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.threes.scenespotinseoul.data.model.Scene

@Dao
interface SceneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scene: Scene): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(scenes: List<Scene>): List<Long>

    @Query("SELECT * FROM scenes WHERE ROWID = :rowId")
    fun loadByRowId(rowId: Long): Scene

    @Query("SELECT * FROM scenes WHERE id = :sceneId")
    fun loadById(sceneId: Int): Scene

    @Query("SELECT * FROM scenes WHERE mediaId = :mediaId")
    fun loadByMediaId(mediaId: Int): List<Scene>

    @Query("SELECT * FROM scenes WHERE locationId = :locationId AND mediaId = :mediaId")
    fun loadByLocationAndMediaId(locationId: Int, mediaId: Int): List<Scene>

    @Query("SELECT * FROM scenes WHERE isCaptured = 1")
    fun loadAllAreCaptured(): List<Scene>

    @Query("SELECT * FROM scenes")
    fun loadAll(): List<Scene>

    @Update
    fun update(scene: Scene)
}