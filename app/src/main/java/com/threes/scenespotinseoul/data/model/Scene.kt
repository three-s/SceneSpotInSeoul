package com.threes.scenespotinseoul.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "scenes",
    indices = [Index("mediaId"), Index("locationId")],
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
    @PrimaryKey(autoGenerate = true) val id: Int,
    val mediaId: Int,
    val locationId: Int,
    val desc: String,
    val image: String,
    var isCaptured: Boolean = false,
    var capturedImage: String?
)