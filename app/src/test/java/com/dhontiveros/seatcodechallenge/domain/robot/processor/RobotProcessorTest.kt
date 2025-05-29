package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.rover_robot.model.helpers.RobotDirection
import com.dhontiveros.rover_robot.model.helpers.RobotMovement
import com.dhontiveros.rover_robot.processor.RobotResult
import com.dhontiveros.rover_robot.processor.RobotProcessor
import com.dhontiveros.commons.robot.RobotInputDto
import com.dhontiveros.rover_robot.processor.ErrorInputRobot
import com.dhontiveros.rover_robot.processor.RobotInputValidator
import com.dhontiveros.seatcodechallenge.domain.robot.processor.commons.SOME_INVALID_JSON
import com.dhontiveros.seatcodechallenge.domain.robot.processor.commons.SOME_VALID_JSON_INVALID_PLATEAU
import com.dhontiveros.seatcodechallenge.domain.robot.processor.commons.SOME_VALID_JSON_INVALID_START_POSITION
import com.dhontiveros.seatcodechallenge.domain.robot.processor.commons.buildRobotInputData
import com.dhontiveros.seatcodechallenge.domain.robot.processor.commons.buildRobotInputJson
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RobotProcessorTest {

    private val robotInputMapper: RobotInputMapper = mockk()
    private val robotInputValidator: RobotInputValidator = mockk()
    private val robotProcessor = RobotProcessor(
        robotInputMapper = robotInputMapper,
        robotInputValidator = robotInputValidator
    )

    @Test
    fun `given an invalid JSON, when move() is called then return return General error`() {
        every { robotInputMapper.toRobotInputJson(SOME_INVALID_JSON) } returns null
        val result = robotProcessor.move(SOME_INVALID_JSON)

        assertTrue(result is RobotResult.Error)
        assertEquals(ErrorInputRobot.General, (result as RobotResult.Error).errorInput)
    }

    @Test
    fun `given an invalid plateau size, when move() function is called then return PlateauSize error`() {
        val input = mockk<RobotInputDto>()
        every { robotInputMapper.toRobotInputJson(SOME_VALID_JSON_INVALID_PLATEAU) } returns input
        every { robotInputValidator.validateInput(input) } returns ErrorInputRobot.PlateauSize

        val result = robotProcessor.move(SOME_VALID_JSON_INVALID_PLATEAU)

        assertTrue(result is RobotResult.Error)
        assertEquals(
            ErrorInputRobot.PlateauSize,
            (result as RobotResult.Error).errorInput
        )
    }

    @Test
    fun `given an invalid start position, when move() function is called then return StartPosition error`() {
        val input = mockk<RobotInputDto>()
        every { robotInputMapper.toRobotInputJson(SOME_VALID_JSON_INVALID_START_POSITION) } returns input
        every { robotInputValidator.validateInput(input) } returns ErrorInputRobot.StartPosition

        val result = robotProcessor.move(SOME_VALID_JSON_INVALID_START_POSITION)

        assertTrue(result is RobotResult.Error)
        assertEquals(
            ErrorInputRobot.StartPosition,
            (result as RobotResult.Error).errorInput
        )
    }

    @Test
    fun `given a valid JSON input, when move() function is called then return Success with all moves applied correctly`() {
        val robotInputJson = buildRobotInputJson()
        val inputData = buildRobotInputData()
        every { robotInputMapper.toRobotInputJson(VALID_JSON) } returns robotInputJson
        every { robotInputValidator.validateInput(robotInputJson) } returns ErrorInputRobot.None(
            inputData
        )

        val result = robotProcessor.move(VALID_JSON)

        assertTrue(result is RobotResult.Success)
        val success = result as RobotResult.Success
        assertEquals(1L, success.robotPosition.posX)
        assertEquals(3L, success.robotPosition.posY)
        assertEquals(RobotDirection.North, success.robotPosition.robotDirection)
        assertEquals(9L, success.movementsApplied)
    }

    @Test
    fun `move() function returns Success but is stopped when robot tries to go out of plateau size`() {
        val robotInputJson = buildRobotInputJson(
            plateauX = 2, plateauY = 2,
            posX = 1, posY = 1,
            direction = "N",
            movements = "MMMMM"
        )
        val inputData = buildRobotInputData(
            plateauX = 1, plateauY = 1,
            posX = 1, posY = 1,
            movements = listOf(RobotMovement.MoveForward, RobotMovement.MoveForward)
        )
        every { robotInputMapper.toRobotInputJson(VALID_JSON) } returns robotInputJson
        every { robotInputValidator.validateInput(robotInputJson) } returns ErrorInputRobot.None(
            inputData
        )

        val result = robotProcessor.move(VALID_JSON)

        assertTrue(result is RobotResult.Success)
        val success = result as RobotResult.Success
        assertTrue(success.movementsApplied < robotInputJson.movements.length)
        assertTrue(success.robotPosition.posY <= robotInputJson.topRightCorner.y)
    }


    private companion object {
        const val VALID_JSON = "valid_json"
    }

}