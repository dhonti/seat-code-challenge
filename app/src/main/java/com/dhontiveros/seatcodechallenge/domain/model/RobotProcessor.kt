package com.dhontiveros.seatcodechallenge.domain.model

import javax.inject.Inject

class RobotProcessor @Inject constructor(
    private val robotInputValidator: RobotInputValidator
) {
    fun move(robotInputData: RobotInputData): ResultMovementRobot {
        val resultValidation = robotInputValidator.validateInput(robotInputData = robotInputData)
        if (resultValidation !is ErrorInputRobot.None) {
            return ResultMovementRobot.Error(typeErrorInput = resultValidation)
        }
        var currentRobotPosition = RobotPosition(
            posX = robotInputData.position.first,
            posY = robotInputData.position.second,
            robotDirection = robotInputData.robotDirection
        )
        robotInputData.movementsList.forEach { movement ->
            currentRobotPosition = currentRobotPosition.updateFromMovement(inputRobotMovement = movement)
        }
        return ResultMovementRobot.Success(robotPosition = currentRobotPosition)
    }
}

sealed class ResultMovementRobot {
    data class Error(val typeErrorInput: ErrorInputRobot) : ResultMovementRobot()
    data class Success(val robotPosition: RobotPosition) : ResultMovementRobot()
}