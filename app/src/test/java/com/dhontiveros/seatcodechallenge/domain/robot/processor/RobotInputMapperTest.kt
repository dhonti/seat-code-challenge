package com.dhontiveros.seatcodechallenge.domain.robot.processor

import com.dhontiveros.seatcodechallenge.domain.robot.processor.commons.SOME_INVALID_JSON
import com.dhontiveros.seatcodechallenge.domain.robot.processor.commons.SOME_VALID_JSON
import com.dhontiveros.presentation.ui.viewmodel.InitialInputData
import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class RobotInputMapperTest {

    private lateinit var robotInputMapper: RobotInputMapper

    @Before
    fun setUp() {
        val moshi = Moshi.Builder().build()
        robotInputMapper = RobotInputMapper(moshi)
    }

    @Test
    fun `The toJsonString() method should serialize InitialInputData to String correctly`() {
        val jsonResult = robotInputMapper.toJsonString(robotInputUiModel = SOME_INITIAL_INPUT_DATA)
        val expectedResult = SOME_VALID_JSON.trimIndent().replace("\\s".toRegex(), "")

        assertEquals(expectedResult, jsonResult)
    }

    @Test
    fun `The toRobotInputJson() method should deserialize JSON String to RobotInputJson`() {
        val result = robotInputMapper.toRobotInputJson(SOME_VALID_JSON)

        assertNotNull(result)
        assertEquals(5L, result!!.topRightCorner.x)
        assertEquals(5L, result.topRightCorner.x)
        assertEquals(1L, result.roverPosition.x)
        assertEquals(2L, result.roverPosition.y)
        assertEquals("N", result.roverDirection)
        assertEquals("LMLMLMLMM", result.movements)
    }

    @Test
    fun `The toRobotInputJson() method should return NULL when receives an invalid JSON`() {
        val result = robotInputMapper.toRobotInputJson(SOME_INVALID_JSON)

        assertNull(result)
    }

    private companion object {
        val SOME_INITIAL_INPUT_DATA = InitialInputData(
            plateauSizeX = 5,
            plateauSizeY = 5,
            posX = 1,
            posY = 2,
            direction = "N",
            movementsList = "LMLMLMLMM"
        )
    }
}