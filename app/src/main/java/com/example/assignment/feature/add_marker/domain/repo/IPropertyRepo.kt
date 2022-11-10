package com.example.assignment.feature.add_marker.domain.repo

import com.example.assignment.core.failure.Failure
import com.example.assignment.core.helpers.Either
import com.example.assignment.feature.add_marker.domain.ui_entity.PropertyUIEntity
import kotlinx.coroutines.flow.Flow

interface IPropertyRepo {
    suspend fun getAllLocation(): Flow<Either<Failure, List<PropertyUIEntity>>>
    suspend fun addProperty(entity: PropertyUIEntity): Flow<Either<Failure, Long>>
}