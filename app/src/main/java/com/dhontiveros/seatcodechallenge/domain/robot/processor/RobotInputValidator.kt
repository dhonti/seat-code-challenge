package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.seatcodechallenge.domain.robot.model.RobotInputData
import com.dhontiveros.seatcodechallenge.domain.robot.model.RobotMovement
import javax.inject.Inject

class RobotInputValidator @Inject constructor() {

    fun validateInput(robotInputData: RobotInputData): ErrorInputRobot {
        if (!validateInputPlateauSize(inputSize = robotInputData.surfaceSize)) return ErrorInputRobot.PlateauSize
        if (!validateInputStarPosition(robotInputData = robotInputData)) return ErrorInputRobot.StartPosition
        if (!validateInputMovements(movementsList = robotInputData.movementsList)) ErrorInputRobot.Movements

        return ErrorInputRobot.None
    }

    private fun validateInputPlateauSize(inputSize: Pair<Long, Long>): Boolean =
        if (inputSize.first == 0L && inputSize.second == 0L) {
            false
        } else if (inputSize.first < 0L || inputSize.second < 0L) {
            false
        } else true

    private fun validateInputStarPosition(robotInputData: RobotInputData): Boolean {
        val startPosition = robotInputData.position
        val plateauSize = robotInputData.surfaceSize
        return !(startPosition.first < 0L ||
                startPosition.second < 0L ||
                startPosition.first > plateauSize.first ||
                startPosition.second > plateauSize.second)
    }

    private fun validateInputMovements(movementsList: List<RobotMovement>): Boolean =
        movementsList.isNotEmpty()

}

sealed class ErrorInputRobot {
    data object None : ErrorInputRobot()
    data object PlateauSize : ErrorInputRobot()
    data object StartPosition : ErrorInputRobot()
    data object Movements : ErrorInputRobot()
}