package com.example.assignment.core.data_base

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PropertyDataBaseEntity(@PrimaryKey(autoGenerate = true) var id: Int=0, var lat:Double, var lng:Double,var propertyName:String)