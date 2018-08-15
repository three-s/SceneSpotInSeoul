package com.threes.scenespotinseoul.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "media_tags",
    primaryKeys = ["tagId", "mediaId"],
    indices = [Index("tagId"), Index("mediaId")],
    foreignKeys = [
        ForeignKey(
            entity = Tag::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tagId")
        ),
        ForeignKey(
            entity = Media::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("mediaId")
        )
    ]
)
data class MediaTag(
    val tagId: Int,
    val mediaId: Int
)