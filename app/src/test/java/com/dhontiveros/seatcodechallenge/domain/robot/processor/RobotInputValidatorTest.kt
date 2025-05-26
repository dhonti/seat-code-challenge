package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJson
import com.dhontiveros.seatcodechallenge.domain.robot.model.input.RobotInputJsonPosition
import org.junit.Assert.assertEquals
import org.junit.Test

class RobotInputValidatorTest {

    private val robotInputValidator: RobotInputValidator = RobotInputValidator()

    @Test
    fun `validateInput() method should return None error when is provided a correct input`() {
        checkInputWithResult(input = SOME_VALID_INPUT, expectedResult = ErrorInputRobot.None)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-PlateauSize when topRightCorner is (0, 0)`() {
        checkInputWithResult(input = SOME_INVALID_PLATEAU_ZERO_INPUT, expectedResult = ErrorInputRobot.PlateauSize)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-PlateauSize when topRightCorner is has negative component`() {
        checkInputWithResult(input = SOME_INVALID_PLATEAU_NEGATIVE_INPUT, expectedResult = ErrorInputRobot.PlateauSize)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartDirection when direction is empty`() {
        checkInputWithResult(input = SOME_INVALID_DIRECTION_EMPTY_INPUT, expectedResult = ErrorInputRobot.StartDirection)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartDirection when direction is longer than 1 char`() {
        checkInputWithResult(input = SOME_INVALID_DIRECTION_LONGER_1_INPUT, expectedResult = ErrorInputRobot.StartDirection)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartDirection when direction has an invalid char`() {
        checkInputWithResult(input = SOME_INVALID_DIRECTION_CHAR_INPUT, expectedResult = ErrorInputRobot.StartDirection)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartPosition when start position has the negative X component`() {
        checkInputWithResult(input = SOME_INVALID_DIRECTION_NEGATIVE_X_COMPONENT_INPUT, expectedResult = ErrorInputRobot.StartPosition)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartPosition when start position has a negative Y component`() {
        checkInputWithResult(input = SOME_INVALID_DIRECTION_NEGATIVE_Y_COMPONENT_INPUT, expectedResult = ErrorInputRobot.StartPosition)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-StartPosition when start position is outside plateau`() {
        checkInputWithResult(input = SOME_INVALID_DIRECTION_OUTSIDE_INPUT, expectedResult = ErrorInputRobot.StartPosition)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-Movements when movements value is empty`() {
        checkInputWithResult(input = SOME_INVALID_MOVEMENTS_EMPTY_INPUT, expectedResult = ErrorInputRobot.Movements)
    }

    @Test
    fun `validateInput() method should return ErrorInputRobot-Movements when movements value has invalid chars`() {
        checkInputWithResult(input = SOME_INVALID_MOVEMENTS_INVALID_CHAR_INPUT, expectedResult = ErrorInputRobot.Movements)
    }

    private fun checkInputWithResult(input: RobotInputJson, expectedResult: ErrorInputRobot){
        val result = robotInputValidator.validateInput(inputData = input)
        assertEquals(expectedResult, result)
    }

    companion object {
        val SOME_VALID_INPUT = RobotInputJson(
            topRightCorner = RobotInputJsonPosition(x = 5, y = 5),
            roverPosition = RobotInputJsonPosition(x = 1, y = 2),
            roverDirection = "N",
            movements = "LMLMLMLMM"
        )
        val SOME_INVALID_PLATEAU_ZERO_INPUT = SOME_VALID_INPUT.copy(
            topRightCorner = RobotInputJsonPosition(x = 0, y = 0)
        )
        val SOME_INVALID_PLATEAU_NEGATIVE_INPUT = SOME_VALID_INPUT.copy(
            topRightCorner = RobotInputJsonPosition(x = -1, y = 5)
        )

        val SOME_INVALID_DIRECTION_EMPTY_INPUT = SOME_VALID_INPUT.copy(roverDirection = "")
        val SOME_INVALID_DIRECTION_LONGER_1_INPUT = SOME_VALID_INPUT.copy(roverDirection = "NE")
        val SOME_INVALID_DIRECTION_CHAR_INPUT = SOME_VALID_INPUT.copy(roverDirection = "T")

        val SOME_INVALID_DIRECTION_NEGATIVE_X_COMPONENT_INPUT = SOME_VALID_INPUT.copy(
            roverPosition = RobotInputJsonPosition(x = -5, y = 5)
        )
        val SOME_INVALID_DIRECTION_NEGATIVE_Y_COMPONENT_INPUT = SOME_VALID_INPUT.copy(
            roverPosition = RobotInputJsonPosition(x = 5, y = -5)
        )
        val SOME_INVALID_DIRECTION_OUTSIDE_INPUT = SOME_VALID_INPUT.copy(
            roverPosition = RobotInputJsonPosition(x = 6, y = 5)
        )

        val SOME_INVALID_MOVEMENTS_EMPTY_INPUT = SOME_VALID_INPUT.copy(movements = "")
        val SOME_INVALID_MOVEMENTS_INVALID_CHAR_INPUT = SOME_VALID_INPUT.copy(movements = "LMT")
    }

}