package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotDirection
import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotMovement
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputData
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJson
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJsonPosition
import javax.inject.Inject

class RobotInputValidator @Inject constructor() {

    fun validateInput(inputData: RobotInputJson): ErrorInputRobot {
        if (!validateInputPlateauSize(inputSize = inputData.topRightCorner)) return ErrorInputRobot.PlateauSize
        if (!validateInputStartDirection(inputDirection = inputData.roverDirection)) return ErrorInputRobot.StartDirection
        if (!validateInputStartPosition(robotInputData = inputData)) return ErrorInputRobot.StartPosition
        if (!validateInputMovements(movementsList = inputData.movements)) return ErrorInputRobot.Movements

        return ErrorInputRobot.None(result = convertInputJsonToInputData(input = inputData))
    }

    private fun validateInputPlateauSize(inputSize: RobotInputJsonPosition): Boolean =
        if (inputSize.x == 0L && inputSize.y == 0L) {
            false
        } else if (inputSize.x < 0L || inputSize.y < 0L) {
            false
        } else true

    private fun validateInputStartDirection(inputDirection: String): Boolean {
        if (inputDirection.isEmpty() || inputDirection.length > 1) return false
        RobotDirection.from(inputDirection[0])?.let { return true } ?: return false
    }

    private fun validateInputStartPosition(robotInputData: RobotInputJson): Boolean {
        val startPosition = robotInputData.roverPosition
        val plateauSize = robotInputData.topRightCorner
        return !(startPosition.x < 0L ||
                startPosition.y < 0L ||
                startPosition.x > plateauSize.x ||
                startPosition.y > plateauSize.y)
    }

    private fun validateInputMovements(movementsList: String): Boolean {
        if (movementsList.isEmpty()) return false
        val result = RobotMovement.parse(input = movementsList)
        return !result.isFailure
    }

    private fun convertInputJsonToInputData(input: RobotInputJson): RobotInputData {
        val startPosition = Pair(first = input.roverPosition.x, second = input.roverPosition.y)
        val surfaceSize = Pair(first = input.topRightCorner.x, second = input.topRightCorner.y)

        val robotDirection = RobotDirection.from(input.roverDirection[0]) ?: RobotDirection.North
        val movementsList = RobotMovement.parse(input.movements).getOrDefault(emptyList())

        return RobotInputData(
            surfaceSize = surfaceSize,
            position = startPosition,
            robotDirection = robotDirection,
            movementsList = movementsList
        )
    }

}

sealed class ErrorInputRobot {
    data class None(val result: RobotInputData) : ErrorInputRobot()
    data object PlateauSize : ErrorInputRobot()
    data object StartDirection : ErrorInputRobot()
    data object StartPosition : ErrorInputRobot()
    data object Movements : ErrorInputRobot()
    data object General : ErrorInputRobot()
}