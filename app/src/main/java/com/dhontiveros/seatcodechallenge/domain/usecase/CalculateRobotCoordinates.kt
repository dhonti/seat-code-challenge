package com.dhontiveros.seatcodechallenge.domain.usecase

import com.dhontiveros.seatcodechallenge.core.di.MainDispatcher
import com.dhontiveros.seatcodechallenge.domain.robot.model.RobotInputData
import com.dhontiveros.seatcodechallenge.domain.robot.processor.ResultMovementRobot
import com.dhontiveros.seatcodechallenge.domain.robot.processor.RobotProcessor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculateRobotCoordinates @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val robotProcessor: RobotProcessor
) {

    suspend operator fun invoke(robotInputData: RobotInputData): ResultMovementRobot =
        withContext(mainDispatcher) {
            robotProcessor.move(robotInputData)
        }

}