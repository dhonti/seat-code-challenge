package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJson
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJsonPosition
import org.junit.Assert.assertEquals
import org.junit.Test

class RobotInputValidatorTest {

    private val robotInputValidator: RobotInputValidator = RobotInputValidator()

    @Test
    fun `validateInput() method should return None error when is provided a correct input`() {
        // Given
        val input = SOME_VALID_INPUT
        // When
        val result = robotInputValidator.validateInput(inputData = input)
        // Then
        assertEquals(ErrorInputRobot.None, result)
    }

    companion object {
        val SOME_VALID_INPUT = RobotInputJson(
            topRightCorner = RobotInputJsonPosition(x = 5, y = 5),
            roverPosition = RobotInputJsonPosition(x = 1, y = 2),
            roverDirection = "N",
            movements = "LMLMLMLMM"
        )
    }

}