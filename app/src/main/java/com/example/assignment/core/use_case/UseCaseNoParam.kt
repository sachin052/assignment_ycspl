package com.example.assignment.core.use_case

import com.example.assignment.core.failure.Failure
import com.example.assignment.core.helpers.Either
import kotlinx.coroutines.flow.Flow

interface UseCaseNoParams<Result>  {
    suspend operator fun invoke(): Flow<Either<Failure, Result>>
}