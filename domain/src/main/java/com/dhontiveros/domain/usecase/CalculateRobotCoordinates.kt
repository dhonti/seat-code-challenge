package com.dhontiveros.domain.usecase

import com.dhontiveros.domain.core.MainDispatcher
import com.dhontiveros.domain.mapper.toDomain
import com.dhontiveros.domain.model.RobotDomainResult
import com.dhontiveros.rover_robot.processor.RobotProcessor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculateRobotCoordinates @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val robotProcessor: RobotProcessor
) {

    suspend operator fun invoke(jsonInput: String): RobotDomainResult =
        withContext(mainDispatcher) {
            robotProcessor.move(jsonInput).toDomain()
        }

}