package com.dhontiveros.rover_robot.processor

import com.dhontiveros.commons.robot.RobotInputDto
import com.dhontiveros.rover_robot.model.RobotErrorInput
import com.dhontiveros.rover_robot.processor.commons.buildRobotInput
import com.dhontiveros.rover_robot.processor.commons.buildRobotInputDto
import org.junit.Assert.assertEquals
import org.junit.Test

class RobotInputValidatorTest {

    private val robotInputValidator: RobotInputValidator = RobotInputValidator()

    @Test
    fun `validateInput() method should return None error when is provided a correct input`() {
        checkInputWithResult(
            input = buildRobotInputDto(),
            expectedResult = RobotErrorInput.None(result = buildRobotInput())
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-PlateauSize when topRightCorner is (0, 0)`() {
        checkInputWithResult(
            input = buildRobotInputDto(plateauX = 0, plateauY = 0),
            expectedResult = RobotErrorInput.PlateauSize
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-PlateauSize when topRightCorner is has negative X component`() {
        checkInputWithResult(
            input = buildRobotInputDto(plateauX = -1),
            expectedResult = RobotErrorInput.PlateauSize
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-PlateauSize when topRightCorner is has negative Y component`() {
        checkInputWithResult(
            input = buildRobotInputDto(plateauY = -1),
            expectedResult = RobotErrorInput.PlateauSize
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartDirection when direction is empty`() {
        checkInputWithResult(
            input = buildRobotInputDto(direction = ""),
            expectedResult = RobotErrorInput.StartDirection
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartDirection when direction is longer than 1 char`() {
        checkInputWithResult(
            input = buildRobotInputDto(direction = "NE"),
            expectedResult = RobotErrorInput.StartDirection
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartDirection when direction has an invalid char`() {
        checkInputWithResult(
            input = buildRobotInputDto(direction = "T"),
            expectedResult = RobotErrorInput.StartDirection
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartPosition when start position has the negative X component`() {
        checkInputWithResult(
            input = buildRobotInputDto(posX = -5),
            expectedResult = RobotErrorInput.StartPosition
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartPosition when start position has a negative Y component`() {
        checkInputWithResult(
            input = buildRobotInputDto(posY = -5),
            expectedResult = RobotErrorInput.StartPosition
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartPosition when start position is outside plateau`() {
        checkInputWithResult(
            input = buildRobotInputDto(posX = 6),
            expectedResult = RobotErrorInput.StartPosition
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-Movements when movements value is empty`() {
        checkInputWithResult(
            input = buildRobotInputDto(movements = ""),
            expectedResult = RobotErrorInput.Movements
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-Movements when movements value has invalid chars`() {
        checkInputWithResult(
            input = buildRobotInputDto(movements = "LMT"),
            expectedResult = RobotErrorInput.Movements
        )
    }

    private fun checkInputWithResult(input: RobotInputDto, expectedResult: RobotErrorInput) {
        val result = robotInputValidator.validateInput(inputDto = input)
        assertEquals(expectedResult, result)
    }
}