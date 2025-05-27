package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotPosition
import javax.inject.Inject

class RobotProcessor @Inject constructor(
    private val robotInputMapper: RobotInputMapper,
    private val robotInputValidator: RobotInputValidator
) {
    fun move(inputJsonString: String): ResultMovementRobot {
        robotInputMapper.toRobotInputJson(jsonString = inputJsonString)?.let { robotInputJsonData ->
            val resultValidation = robotInputValidator.validateInput(inputData = robotInputJsonData)
            if (resultValidation !is ErrorInputRobot.None) {
                return ResultMovementRobot.Error(typeErrorInput = resultValidation)
            }

            val robotInputData = resultValidation.result
            var currentPosition = RobotPosition(
                posX = robotInputData.position.first,
                posY = robotInputData.position.second,
                robotDirection = robotInputData.robotDirection
            )
            var targetPosition: RobotPosition
            var movementsApplied = 0L
            robotInputData.movementsList.forEach { movement ->
                targetPosition =
                    currentPosition.updateFromMovement(inputRobotMovement = movement)
                if (targetPosition.isInBounds(
                        robotInputData.surfaceSize.first,
                        robotInputData.surfaceSize.second
                    )
                ) {
                    currentPosition = targetPosition
                    ++movementsApplied
                } else {
                    return@forEach
                }

            }
            return ResultMovementRobot.Success(
                robotPosition = currentPosition,
                movementsApplied = movementsApplied
            )
        } ?: return ResultMovementRobot.Error(typeErrorInput = ErrorInputRobot.General)
    }
}

sealed class ResultMovementRobot {
    data class Error(val typeErrorInput: ErrorInputRobot) : ResultMovementRobot()
    data class Success(val robotPosition: RobotPosition, val movementsApplied: Long) :
        ResultMovementRobot()
}