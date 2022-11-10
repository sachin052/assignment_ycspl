package com.example.assignment.core.data_base.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.core.data_base.PropertyDataBaseEntity
import java.nio.channels.Channels

@Dao
interface PropertyEntityDao {

    @Query("SELECT * FROM propertydatabaseentity")
    suspend fun getAllLocations(): List<PropertyDataBaseEntity>

    @Insert
   suspend fun insertLocation(entity: PropertyDataBaseEntity): Long
}