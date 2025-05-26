package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotDirection
import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotMovement
import com.dhontiveros.seatcodechallenge.domain.robot.model.attrs.RobotPosition
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputData
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJson
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

            val robotInputData = convertInputJsonToInputData(input = robotInputJsonData)
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

    private fun convertInputJsonToInputData(input: RobotInputJson): RobotInputData {
        val startPosition = Pair(first = input.roverPosition.x, second = input.roverPosition.y)
        val surfaceSize = Pair(first = input.topRightCorner.x, second = input.topRightCorner.y)
        val robotDirection = RobotDirection.from(input.roverDirection[0]) ?: RobotDirection.North
        val movementsList = RobotMovement.parse(input.movements).getOrDefault(listOf())

        return RobotInputData(
            surfaceSize = surfaceSize,
            position = startPosition,
            robotDirection = robotDirection,
            movementsList = movementsList
        )
    }
}

sealed class ResultMovementRobot {
    data class Error(val typeErrorInput: ErrorInputRobot) : ResultMovementRobot()
    data class Success(val robotPosition: RobotPosition, val movementsApplied: Long) :
        ResultMovementRobot()
}