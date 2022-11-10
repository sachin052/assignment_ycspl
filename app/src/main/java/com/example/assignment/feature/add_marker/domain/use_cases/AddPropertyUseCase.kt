package com.example.assignment.feature.add_marker.domain.use_cases

import com.example.assignment.core.failure.Failure
import com.example.assignment.core.helpers.Either
import com.example.assignment.core.use_case.UseCase
import com.example.assignment.feature.add_marker.domain.repo.IPropertyRepo
import com.example.assignment.feature.add_marker.domain.ui_entity.PropertyUIEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddPropertyUseCase @Inject constructor(private val propertyRepoImpl: IPropertyRepo) :
    UseCase<PropertyUIEntity, Long> {
    override suspend fun invoke(params: PropertyUIEntity): Flow<Either<Failure, Long>> {
        return propertyRepoImpl.addProperty(params)
    }
}