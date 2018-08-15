package com.threes.scenespotinseoul.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.threes.scenespotinseoul.data.model.Location

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: Location): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locations: List<Location>): List<Long>

    @Query("SELECT * FROM locations WHERE ROWID = :rowId")
    fun loadByRowId(rowId: Long): Location

    @Query("SELECT * FROM locations WHERE id = :locationId")
    fun loadById(locationId: Int): Location

    @Query("SELECT * FROM locations WHERE name = :name")
    fun loadByName(name: String): Location

    @Query("SELECT * FROM locations WHERE isCaptured = 1")
    fun loadAllAreCaptured(): List<Location>

    @Query("SELECT * FROM locations")
    fun loadAll(): List<Location>

    @Update
    fun update(location: Location)
}