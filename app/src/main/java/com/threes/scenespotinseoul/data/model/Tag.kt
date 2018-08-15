package com.threes.scenespotinseoul.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tags",
    indices = [Index("name", unique = true)]
)
data class Tag(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)