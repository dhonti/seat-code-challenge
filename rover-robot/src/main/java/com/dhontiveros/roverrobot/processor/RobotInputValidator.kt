package com.dhontiveros.roverrobot.processor

import com.dhontiveros.commons.robot.RobotInputDto
import com.dhontiveros.commons.robot.RobotInputPositionDto
import com.dhontiveros.roverrobot.model.RobotErrorInput
import com.dhontiveros.roverrobot.model.RobotInput
import com.dhontiveros.roverrobot.model.helpers.RobotDirection
import com.dhontiveros.roverrobot.model.helpers.RobotMovement
import javax.inject.Inject

internal class RobotInputValidator @Inject constructor() {

    fun validateInput(inputDto: RobotInputDto): RobotErrorInput {
        if (!validateInputPlateauSize(inputSize = inputDto.topRightCorner)) return RobotErrorInput.PlateauSize
        if (!validateInputStartDirection(inputDirection = inputDto.roverDirection)) return RobotErrorInput.StartDirection
        if (!validateInputStartPosition(inputDto = inputDto)) return RobotErrorInput.StartPosition
        if (!validateInputMovements(movementsList = inputDto.movements)) return RobotErrorInput.Movements

        return RobotErrorInput.None(result = convertInputJsonToInputData(inputDto = inputDto))
    }

    private fun validateInputPlateauSize(inputSize: RobotInputPositionDto): Boolean =
        if (inputSize.x == 0L && inputSize.y == 0L) {
            false
        } else if (inputSize.x < 0L || inputSize.y < 0L) {
            false
        } else true

    private fun validateInputStartDirection(inputDirection: String): Boolean {
        if (inputDirection.isEmpty() || inputDirection.length > 1) return false
        RobotDirection.from(inputDirection[0])?.let { return true } ?: return false
    }

    private fun validateInputStartPosition(inputDto: RobotInputDto): Boolean {
        val startPosition = inputDto.roverPosition
        val plateauSize = inputDto.topRightCorner
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

    private fun convertInputJsonToInputData(inputDto: RobotInputDto): RobotInput {
        val startPosition =
            Pair(first = inputDto.roverPosition.x, second = inputDto.roverPosition.y)
        val surfaceSize =
            Pair(first = inputDto.topRightCorner.x, second = inputDto.topRightCorner.y)

        val robotDirection = RobotDirection.from(inputDto.roverDirection[0]) ?: RobotDirection.North
        val movementsList = RobotMovement.parse(inputDto.movements).getOrDefault(emptyList())

        return RobotInput(
            surfaceSize = surfaceSize,
            position = startPosition,
            robotDirection = robotDirection,
            movementsList = movementsList
        )
    }

}
