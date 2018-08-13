package com.threes.scenespotinseoul.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "media")
data class Media(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val desc: String,
    val image: String
)