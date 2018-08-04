package com.threes.scenespotinseoul.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import android.arch.persistence.room.Update
import com.threes.scenespotinseoul.data.model.Scene

@Dao
interface SceneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scene: Scene)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(scenes: List<Scene>)

    @Query("SELECT * FROM scenes WHERE locationId = :locationId AND mediaId = :mediaId")
    fun loadById(locationId: Int, mediaId: Int): Scene

    @Transaction
    @Query("SELECT * FROM scenes WHERE isCaptured = 1")
    fun loadAllAreCaptured(): List<Scene>

    @Transaction
    @Query("SELECT * FROM scenes")
    fun loadAll(): List<Scene>

    @Update
    fun update(scene: Scene)
}