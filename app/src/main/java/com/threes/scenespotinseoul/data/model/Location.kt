package com.threes.scenespotinseoul.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val lat: Double,
    val lon: Double,
    val name: String,
    val desc: String,
    val address: String,
    val image: String,
    var isCaptured: Boolean = false
)