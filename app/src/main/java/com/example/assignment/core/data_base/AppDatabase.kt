package com.example.assignment.core.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.core.data_base.dao.PropertyEntityDao

@Database(entities = [PropertyDataBaseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelDao(): PropertyEntityDao
}