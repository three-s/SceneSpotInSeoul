package com.threes.scenespotinseoul.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index

@Entity(
    tableName = "location_tags",
    primaryKeys = ["tagId", "locationId"],
    indices = [Index("tagId"), Index("locationId")],
    foreignKeys = [
        ForeignKey(
            entity = Tag::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tagId")
        ),
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("locationId")
        )
    ]
)
data class LocationTag(
    val tagId: Int,
    val locationId: Int
)