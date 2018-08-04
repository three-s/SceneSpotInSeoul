package com.threes.scenespotinseoul.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index

@Entity(
    tableName = "scenes",
    indices = [Index("mediaId"), Index("locationId")],
    primaryKeys = ["mediaId", "locationId"],
    foreignKeys = [
        ForeignKey(
            entity = Media::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("mediaId")
        ),
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("locationId")
        )
    ]
)
data class Scene(
    val mediaId: Int,
    val locationId: Int,
    var isCaptured: Boolean,
    val image: String,
    val desc: String,
    val capturedImage: String
)