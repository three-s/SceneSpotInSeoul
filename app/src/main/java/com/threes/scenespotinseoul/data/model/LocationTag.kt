package com.threes.scenespotinseoul.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import com.threes.scenespotinseoul.utilities.LOCATION_TAG_TABLE

@Entity(
    tableName = LOCATION_TAG_TABLE,
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
            parentColumns = arrayOf("uuid"),
            childColumns = arrayOf("locationId")
        )
    ]
)
data class LocationTag(
    val tagId: Int,
    val locationId: String
)