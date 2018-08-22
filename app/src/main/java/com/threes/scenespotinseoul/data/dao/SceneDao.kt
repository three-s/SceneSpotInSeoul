package com.threes.scenespotinseoul.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.threes.scenespotinseoul.data.model.Scene

@Dao
interface SceneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scene: Scene): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(scenes: List<Scene>): List<Long>

    @Query("SELECT * FROM scenes WHERE ROWID = :rowId")
    fun loadByRowId(rowId: Long): Scene

    @Query("SELECT * FROM scenes WHERE ROWID = :rowId")
    fun loadByRowIdWithLive(rowId: Long): LiveData<Scene>

    @Query("SELECT * FROM scenes WHERE id = :sceneId")
    fun loadById(sceneId: Int): Scene

    @Query("SELECT * FROM scenes WHERE id = :sceneId")
    fun loadByIdWithLive(sceneId: Int): LiveData<Scene>

    @Query("SELECT * FROM scenes WHERE mediaId = :mediaId")
    fun loadByMediaId(mediaId: Int): List<Scene>

    @Query("SELECT * FROM scenes WHERE locationId = :locationId AND mediaId = :mediaId")
    fun loadByLocationAndMediaId(locationId: Int, mediaId: Int): List<Scene>

    @Query("SELECT * FROM scenes WHERE isCaptured = 1")
    fun loadAllAreCaptured(): List<Scene>

    @Query("SELECT * FROM scenes WHERE isCaptured = 1")
    fun loadAllAreCapturedWithLive(): LiveData<List<Scene>>

    @Query("SELECT * FROM scenes")
    fun loadAll(): List<Scene>

    @Update
    fun update(scene: Scene)
}