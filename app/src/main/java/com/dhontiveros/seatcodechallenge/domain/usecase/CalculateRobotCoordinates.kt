package com.dhontiveros.seatcodechallenge.domain.usecase

import com.dhontiveros.seatcodechallenge.core.di.MainDispatcher
import com.dhontiveros.seatcodechallenge.domain.robot.processor.ResultMovementRobot
import com.dhontiveros.seatcodechallenge.domain.robot.processor.RobotInputMapper
import com.dhontiveros.seatcodechallenge.domain.robot.processor.RobotProcessor
import com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel.InitialInputData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalculateRobotCoordinates @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val robotInputMapper: RobotInputMapper,
    private val robotProcessor: RobotProcessor
) {

    suspend operator fun invoke(inputData: InitialInputData): ResultMovementRobot =
        withContext(mainDispatcher) {
            robotProcessor.move(robotInputMapper.toJsonString(initialInputData = inputData))
        }

}