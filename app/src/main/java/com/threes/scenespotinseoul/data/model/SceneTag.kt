package com.threes.scenespotinseoul.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import com.threes.scenespotinseoul.utilities.SCENE_TAG_TABLE

@Entity(
    tableName = SCENE_TAG_TABLE,
    primaryKeys = ["tagId", "sceneId"],
    indices = [Index("tagId"), Index("sceneId")],
    foreignKeys = [
        ForeignKey(
            entity = Tag::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tagId")
        ),
        ForeignKey(
            entity = Scene::class,
            parentColumns = arrayOf("uuid"),
            childColumns = arrayOf("sceneId")
        )
    ]
)
data class SceneTag(
    val tagId: Int,
    val sceneId: String
)