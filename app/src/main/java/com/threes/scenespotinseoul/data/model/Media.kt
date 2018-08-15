package com.threes.scenespotinseoul.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media")
data class Media(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val desc: String,
    val image: String
)