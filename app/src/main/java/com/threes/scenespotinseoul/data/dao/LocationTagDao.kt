package com.threes.scenespotinseoul.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.threes.scenespotinseoul.data.model.LocationTag

@Dao
interface LocationTagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(locationTag: LocationTag): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(locationTags: List<LocationTag>): List<Long>

    @Query("SELECT * FROM location_tags WHERE locationId = :locationId")
    fun loadByLocationId(locationId: Int): List<LocationTag>

    @Query("SELECT * FROM location_tags WHERE tagId = :tagId")
    fun loadByTagId(tagId: Int): List<LocationTag>
}