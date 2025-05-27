package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJson
import com.dhontiveros.seatcodechallenge.domain.robot.processor.commons.buildRobotInputData
import com.dhontiveros.seatcodechallenge.domain.robot.processor.commons.buildRobotInputJson
import org.junit.Assert.assertEquals
import org.junit.Test

class RobotInputValidatorTest {

    private val robotInputValidator: RobotInputValidator = RobotInputValidator()

    @Test
    fun `validateInput() method should return None error when is provided a correct input`() {
        checkInputWithResult(
            input = buildRobotInputJson(),
            expectedResult = ErrorInputRobot.None(result = buildRobotInputData())
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-PlateauSize when topRightCorner is (0, 0)`() {
        checkInputWithResult(
            input = buildRobotInputJson(plateauX = 0, plateauY = 0),
            expectedResult = ErrorInputRobot.PlateauSize
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-PlateauSize when topRightCorner is has negative X component`() {
        checkInputWithResult(
            input = buildRobotInputJson(plateauX = -1),
            expectedResult = ErrorInputRobot.PlateauSize
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-PlateauSize when topRightCorner is has negative Y component`() {
        checkInputWithResult(
            input = buildRobotInputJson(plateauY = -1),
            expectedResult = ErrorInputRobot.PlateauSize
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartDirection when direction is empty`() {
        checkInputWithResult(
            input = buildRobotInputJson(direction = ""),
            expectedResult = ErrorInputRobot.StartDirection
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartDirection when direction is longer than 1 char`() {
        checkInputWithResult(
            input = buildRobotInputJson(direction = "NE"),
            expectedResult = ErrorInputRobot.StartDirection
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartDirection when direction has an invalid char`() {
        checkInputWithResult(
            input = buildRobotInputJson(direction = "T"),
            expectedResult = ErrorInputRobot.StartDirection
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartPosition when start position has the negative X component`() {
        checkInputWithResult(
            input = buildRobotInputJson(posX = -5),
            expectedResult = ErrorInputRobot.StartPosition
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartPosition when start position has a negative Y component`() {
        checkInputWithResult(
            input = buildRobotInputJson(posY = -5),
            expectedResult = ErrorInputRobot.StartPosition
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartPosition when start position is outside plateau`() {
        checkInputWithResult(
            input = buildRobotInputJson(posX = 6),
            expectedResult = ErrorInputRobot.StartPosition
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-Movements when movements value is empty`() {
        checkInputWithResult(
            input = buildRobotInputJson(movements = ""),
            expectedResult = ErrorInputRobot.Movements
        )
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-Movements when movements value has invalid chars`() {
        checkInputWithResult(
            input = buildRobotInputJson(movements = "LMT"),
            expectedResult = ErrorInputRobot.Movements
        )
    }

    private fun checkInputWithResult(input: RobotInputJson, expectedResult: ErrorInputRobot) {
        val result = robotInputValidator.validateInput(inputData = input)
        assertEquals(expectedResult, result)
    }
}