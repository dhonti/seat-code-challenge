package com.dhontiveros.roverrobot.processor

import com.dhontiveros.commons.robot.toJsonString
import com.dhontiveros.roverrobot.model.RobotErrorInput
import com.dhontiveros.roverrobot.model.RobotResult
import com.dhontiveros.roverrobot.model.helpers.RobotDirection
import com.dhontiveros.roverrobot.model.helpers.RobotMovement
import com.dhontiveros.roverrobot.processor.commons.SOME_INVALID_JSON
import com.dhontiveros.roverrobot.processor.commons.SOME_VALID_JSON
import com.dhontiveros.roverrobot.processor.commons.VALID_JSON_OUT_PLATEAU
import com.dhontiveros.roverrobot.processor.commons.buildRobotInput
import com.dhontiveros.roverrobot.processor.commons.buildRobotInputDto
import com.squareup.moshi.Moshi
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RobotProcessorTest {

    private val robotInputValidator: RobotInputValidator = mockk()
    private val moshi: Moshi = Moshi.Builder().build()
    private val robotProcessor = RobotProcessor(
        moshi = moshi,
        robotInputValidator = robotInputValidator
    )

    @Test
    fun `given an invalid JSON, when move() is called, then return returns General error`() {
        val result = robotProcessor.move(SOME_INVALID_JSON)

        assertTrue(result is RobotResult.Error)
        assertEquals(RobotErrorInput.General, (result as RobotResult.Error).errorInput)
    }

    @Test
    fun `given an invalid plateau size, when move() function is called, then returns PlateauSize error`() {
        val input = buildRobotInputDto(plateauX = -5)
        every { robotInputValidator.validateInput(input) } returns RobotErrorInput.PlateauSize

        val result = robotProcessor.move(input.toJsonString(moshi))

        assertTrue(result is RobotResult.Error)
        assertEquals(RobotErrorInput.PlateauSize, (result as RobotResult.Error).errorInput)
    }

    @Test
    fun `given an invalid start position, when move() function is called, then returns StartPosition error`() {
        val input = buildRobotInputDto(posX = -5)
        every { robotInputValidator.validateInput(input) } returns RobotErrorInput.StartPosition

        val result = robotProcessor.move(input.toJsonString(moshi))

        assertTrue(result is RobotResult.Error)
        assertEquals(RobotErrorInput.StartPosition, (result as RobotResult.Error).errorInput)
    }

    @Test
    fun `given a valid JSON input, when move() function is called, then returns Success with all moves applied correctly`() {
        val robotInputJson = buildRobotInputDto()
        val inputData = buildRobotInput()
        every { robotInputValidator.validateInput(robotInputJson) } returns RobotErrorInput.None(
            inputData
        )

        val result = robotProcessor.move(SOME_VALID_JSON)

        assertTrue(result is RobotResult.Success)
        val success = result as RobotResult.Success
        assertEquals(1L, success.robotOutput.position.x)
        assertEquals(3L, success.robotOutput.position.y)
        assertEquals(RobotDirection.North, success.robotOutput.position.direction)
        assertEquals(robotInputJson.movements.length.toLong(), success.robotOutput.movementsApplied)
    }

    @Test
    fun `move() function returns Success but is stopped at plateau limit, when robot tries to go out of plateau size`() {
        val inputDto = buildRobotInputDto(
            plateauX = 2, plateauY = 2,
            posX = 1, posY = 1,
            direction = "N",
            movements = "MMMMM"
        )
        val inputData = buildRobotInput(
            plateauX = 2, plateauY = 2,
            posX = 1, posY = 1,
            movements = listOf(
                RobotMovement.MoveForward,
                RobotMovement.MoveForward,
                RobotMovement.MoveForward,
                RobotMovement.MoveForward,
                RobotMovement.MoveForward
            )
        )
        every { robotInputValidator.validateInput(inputDto) } returns RobotErrorInput.None(
            inputData
        )

        val result = robotProcessor.move(VALID_JSON_OUT_PLATEAU)

        assertTrue(result is RobotResult.Success)
        val success = result as RobotResult.Success
        assertTrue(success.robotOutput.movementsApplied < inputDto.movements.length)
        assertTrue(success.robotOutput.position.y <= inputDto.topRightCorner.y)
    }

}