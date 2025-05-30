package com.dhontiveros.domain.usecase

import com.dhontiveros.commons.core.di.dispatchers.DefaultDispatcher
import com.dhontiveros.commons.core.di.result.Either
import com.dhontiveros.domain.mapper.toDomain
import com.dhontiveros.domain.model.RobotDomainErrorInput
import com.dhontiveros.domain.model.RobotDomainOutput
import com.dhontiveros.roverrobot.processor.RobotProcessor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculateRobotCoordinates @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val robotProcessor: RobotProcessor
) {

    suspend operator fun invoke(jsonInput: String): Either<RobotDomainErrorInput, RobotDomainOutput> =
        withContext(defaultDispatcher) {
            robotProcessor.move(jsonInput).fold(
                left = { error ->
                    Either.Left(error.toDomain())
                },
                right = { response ->
                    Either.Right(response.toDomain())
                }
            )
        }
}