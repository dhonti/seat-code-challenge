package com.dhontiveros.roverrobot.processor

import com.dhontiveros.commons.core.di.result.Either
import com.dhontiveros.commons.robot.RobotInputDto
import com.dhontiveros.roverrobot.model.RobotErrorInput
import com.dhontiveros.roverrobot.model.RobotOutput
import com.dhontiveros.roverrobot.model.helpers.RobotPosition
import com.squareup.moshi.Moshi
import javax.inject.Inject

internal class RobotProcessorImpl @Inject constructor(
    private val moshi: Moshi,
    private val robotInputValidator: RobotInputValidator
) : RobotProcessor {
    override fun move(inputJsonString: String): Either<RobotErrorInput, RobotOutput> {
        inputJsonString.toDto(moshi)?.let { dto ->
            val resultValidation = robotInputValidator.validateInput(inputDto = dto)
            if (resultValidation !is RobotErrorInput.None) {
                return Either.Left(resultValidation)
            }

            val robotInput = resultValidation.result
            var currentPosition = RobotPosition(
                x = robotInput.position.first,
                y = robotInput.position.second,
                direction = robotInput.robotDirection
            )
            var targetPosition: RobotPosition
            var movementsApplied = 0L
            robotInput.movementsList.forEach { movement ->
                targetPosition =
                    currentPosition.updateFromMovement(inputRobotMovement = movement)
                if (targetPosition.isInBounds(
                        robotInput.surfaceSize.first,
                        robotInput.surfaceSize.second
                    )
                ) {
                    currentPosition = targetPosition
                    ++movementsApplied
                } else {
                    return@forEach
                }

            }
            return Either.Right(
                RobotOutput(
                    position = currentPosition,
                    movementsApplied = movementsApplied
                )
            )
        } ?: return Either.Left(RobotErrorInput.General)
    }
}

private fun String.toDto(moshi: Moshi): RobotInputDto? =
    try {
        moshi.adapter(RobotInputDto::class.java).fromJson(this)
    } catch (_: Exception) {
        null
    }