package com.example.assignment.feature.add_marker.data.repo_impl

import com.example.assignment.core.data_base.dao.PropertyEntityDao
import com.example.assignment.core.failure.Failure
import com.example.assignment.core.helpers.Either
import com.example.assignment.feature.add_marker.data.mapper.Mapper
import com.example.assignment.feature.add_marker.domain.repo.IPropertyRepo
import com.example.assignment.feature.add_marker.domain.ui_entity.PropertyUIEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PropertyRepoImpl @Inject constructor(private val dao:PropertyEntityDao): IPropertyRepo {
    override suspend fun getAllLocation(): Flow<Either<Failure, List<PropertyUIEntity>>> {
        return flow {
            val items=dao.getAllLocations()
            emit(Either.Right(items.map(Mapper::toUIEntity)))
        }
    }

    override suspend fun addProperty(entity: PropertyUIEntity): Flow<Either<Failure, Long>> {
        return flow {
            emit(Either.Right(dao.insertLocation(Mapper.toDBEntity(entity))))
        }
    }
}