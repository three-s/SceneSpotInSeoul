package com.threes.scenespotinseoul.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

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
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mediaId: Int,
    val locationId: Int,
    val desc: String,
    val image: String,
    var isCaptured: Boolean = false,
    var capturedImage: String? = null
)