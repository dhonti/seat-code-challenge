package com.dhontiveros.roverrobot.processor

import com.dhontiveros.commons.robot.RobotInputDto
import com.dhontiveros.roverrobot.model.RobotErrorInput
import com.dhontiveros.roverrobot.model.RobotOutput
import com.dhontiveros.roverrobot.model.RobotResult
import com.dhontiveros.roverrobot.model.helpers.RobotPosition
import com.squareup.moshi.Moshi
import javax.inject.Inject

class RobotProcessor @Inject constructor(
    private val moshi: Moshi,
    private val robotInputValidator: RobotInputValidator
) {
    fun move(inputJsonString: String): RobotResult {
        inputJsonString.toDto(moshi)?.let { dto ->
            val resultValidation = robotInputValidator.validateInput(inputDto = dto)
            if (resultValidation !is RobotErrorInput.None) {
                return RobotResult.Error(errorInput = resultValidation)
            }

            val robotInputData = resultValidation.result
            var currentPosition = RobotPosition(
                x = robotInputData.position.first,
                y = robotInputData.position.second,
                direction = robotInputData.robotDirection
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
            return RobotResult.Success(
                robotOutput = RobotOutput(
                    position = currentPosition,
                    movementsApplied = movementsApplied
                ),
            )
        } ?: return RobotResult.Error(errorInput = RobotErrorInput.General)
    }
}

private fun String.toDto(moshi: Moshi): RobotInputDto? =
    try {
        moshi.adapter(RobotInputDto::class.java).fromJson(this)
    } catch (_: Exception) {
        null
    }