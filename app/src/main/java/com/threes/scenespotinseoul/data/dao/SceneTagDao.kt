package com.threes.scenespotinseoul.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.threes.scenespotinseoul.data.model.SceneTag

@Dao
interface SceneTagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(sceneTag: SceneTag): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(sceneTags: List<SceneTag>): List<Long>

    @Query("SELECT * FROM scene_tags WHERE sceneId = :sceneId")
    fun loadBySceneId(sceneId: Int): List<SceneTag>

    @Query("SELECT * FROM scene_tags WHERE tagId = :tagId")
    fun loadByTagId(tagId: Int): List<SceneTag>
}