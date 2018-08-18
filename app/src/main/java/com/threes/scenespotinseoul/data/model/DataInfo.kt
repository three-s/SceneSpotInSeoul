package com.threes.scenespotinseoul.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "data_info")
data class DataInfo(
    @PrimaryKey val id: Int = 0,
    var updatedDate: Long
)