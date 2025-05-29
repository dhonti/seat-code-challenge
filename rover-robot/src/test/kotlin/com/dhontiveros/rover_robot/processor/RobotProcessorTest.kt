package com.dhontiveros.rover_robot.processor

import com.dhontiveros.commons.robot.toJsonString
import com.dhontiveros.rover_robot.model.RobotErrorInput
import com.dhontiveros.rover_robot.model.RobotResult
import com.dhontiveros.rover_robot.model.helpers.RobotDirection
import com.dhontiveros.rover_robot.model.helpers.RobotMovement
import com.dhontiveros.rover_robot.processor.commons.SOME_INVALID_JSON
import com.dhontiveros.rover_robot.processor.commons.SOME_VALID_JSON_INVALID_PLATEAU
import com.dhontiveros.rover_robot.processor.commons.SOME_VALID_JSON_INVALID_START_POSITION
import com.dhontiveros.rover_robot.processor.commons.buildRobotInput
import com.dhontiveros.rover_robot.processor.commons.buildRobotInputDto
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
    fun `given an invalid JSON, when move() is called then return return General error`() {
        val result = robotProcessor.move(SOME_INVALID_JSON)

        assertTrue(result is RobotResult.Error)
        assertEquals(RobotErrorInput.General, (result as RobotResult.Error).errorInput)
    }

    @Test
    fun `given an invalid plateau size, when move() function is called then return PlateauSize error`() {
        val input = buildRobotInputDto(plateauX = -5)
        every { robotInputValidator.validateInput(input) } returns RobotErrorInput.PlateauSize

        val result = robotProcessor.move(input.toJsonString(moshi))

        assertTrue(result is RobotResult.Error)
        assertEquals(
            RobotErrorInput.PlateauSize,
            (result as RobotResult.Error).errorInput
        )
    }

    @Test
    fun `given an invalid start position, when move() function is called then return StartPosition error`() {
        val input = buildRobotInputDto(posX = -5)
        every { robotInputValidator.validateInput(input) } returns RobotErrorInput.StartPosition

        val result = robotProcessor.move(input.toJsonString(moshi))

        assertTrue(result is RobotResult.Error)
        assertEquals(
            RobotErrorInput.StartPosition,
            (result as RobotResult.Error).errorInput
        )
    }

    @Test
    fun `given a valid JSON input, when move() function is called then return Success with all moves applied correctly`() {
        val robotInputJson = buildRobotInputDto()
        val inputData = buildRobotInput()
        every { robotInputValidator.validateInput(robotInputJson) } returns RobotErrorInput.None(
            inputData
        )

        val result = robotProcessor.move(VALID_JSON)

        assertTrue(result is RobotResult.Success)
        val success = result as RobotResult.Success
        assertEquals(1L, success.robotOutput.position.x)
        assertEquals(3L, success.robotOutput.position.x)
        assertEquals(RobotDirection.North, success.robotOutput.position.direction)
        assertEquals(9L, success.robotOutput.movementsApplied)
    }

    @Test
    fun `move() function returns Success but is stopped when robot tries to go out of plateau size`() {
        val robotInputJson = buildRobotInputDto(
            plateauX = 2, plateauY = 2,
            posX = 1, posY = 1,
            direction = "N",
            movements = "MMMMM"
        )
        val inputData = buildRobotInput(
            plateauX = 1, plateauY = 1,
            posX = 1, posY = 1,
            movements = listOf(RobotMovement.MoveForward, RobotMovement.MoveForward)
        )
        every { robotInputValidator.validateInput(robotInputJson) } returns RobotErrorInput.None(
            inputData
        )

        val result = robotProcessor.move(VALID_JSON)

        assertTrue(result is RobotResult.Success)
        val success = result as RobotResult.Success
        assertTrue(success.robotOutput.movementsApplied < robotInputJson.movements.length)
        assertTrue(success.robotOutput.position.y <= robotInputJson.topRightCorner.y)
    }


    private companion object {
        const val VALID_JSON = "valid_json"
    }

}