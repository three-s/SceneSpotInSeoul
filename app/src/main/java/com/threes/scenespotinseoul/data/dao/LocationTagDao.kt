package com.threes.scenespotinseoul.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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