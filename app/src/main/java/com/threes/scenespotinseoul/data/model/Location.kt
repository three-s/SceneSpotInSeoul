package com.threes.scenespotinseoul.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val lat: Double,
    val lon: Double,
    val address: String,
    val desc: String,
    val image: String,
    var isCaptured: Boolean
)