package com.threes.scenespotinseoul.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index

@Entity(
    tableName = "scene_tags",
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
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sceneId")
        )
    ]
)
data class SceneTag(
    val tagId: Int,
    val sceneId: Int
)