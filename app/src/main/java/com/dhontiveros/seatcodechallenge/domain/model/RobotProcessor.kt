package com.dhontiveros.seatcodechallenge.domain.model

import javax.inject.Inject

class RobotProcessor @Inject constructor() {

    fun move(inputData: InputData): ResultMovementRobot {
        val resultValidation = RobotInputValidator.validateInput(inputData = inputData)
        if (resultValidation !is ErrorInputRobot.None) {
            return ResultMovementRobot.Error(typeErrorInput = resultValidation)
        }
        var currentPosition = Position(
            posX = inputData.position.first,
            posY = inputData.position.second,
            direction = inputData.direction
        )
        inputData.movementsList.forEach { movement ->
            currentPosition = currentPosition.updateFromMovement(inputMovement = movement)
        }
        return ResultMovementRobot.Success(position = currentPosition)
    }
}

sealed class ResultMovementRobot {
    data class Error(val typeErrorInput: ErrorInputRobot) : ResultMovementRobot()
    data class Success(val position: Position) : ResultMovementRobot()
}