package com.threes.scenespotinseoul.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import android.arch.persistence.room.Update
import com.threes.scenespotinseoul.data.model.Location

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: Location)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locations: List<Location>)

    @Transaction
    @Query("SELECT * FROM locations WHERE id = :locationId")
    fun loadById(locationId: Int): Location

    @Transaction
    @Query("SELECT * FROM locations WHERE isCaptured = 1")
    fun loadAllAreCaptured(): List<Location>

    @Transaction
    @Query("SELECT * FROM locations")
    fun loadAll(): List<Location>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(location: Location)
}