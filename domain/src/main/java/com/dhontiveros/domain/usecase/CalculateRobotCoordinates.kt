package com.dhontiveros.domain.usecase

import com.dhontiveros.domain.core.DefaultDispatcher
import com.dhontiveros.domain.mapper.toDomain
import com.dhontiveros.domain.model.RobotDomainResult
import com.dhontiveros.roverrobot.processor.RobotProcessor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculateRobotCoordinates @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val robotProcessor: RobotProcessor
) {

    suspend operator fun invoke(jsonInput: String): RobotDomainResult =
        withContext(defaultDispatcher) {
            robotProcessor.move(jsonInput).toDomain()
        }

}