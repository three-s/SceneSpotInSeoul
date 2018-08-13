package com.threes.scenespotinseoul.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.threes.scenespotinseoul.data.model.SceneTag

@Dao
interface SceneTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sceneTag: SceneTag): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sceneTags: List<SceneTag>): List<Long>

    @Query("SELECT * FROM scene_tags WHERE sceneId = :sceneId")
    fun loadBySceneId(sceneId: Int): List<SceneTag>

    @Query("SELECT * FROM scene_tags WHERE tagId = :tagId")
    fun loadByTagId(tagId: Int): List<SceneTag>
}