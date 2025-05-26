package com.dhontiveros.seatcodechallenge.domain.robot

import com.dhontiveros.seatcodechallenge.domain.robot.processor.RobotInputMapper
import com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel.InitialInputData
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
        // Given
        val inputData = SOME_INITIAL_INPUT_DATA
        // When
        val jsonResult = robotInputMapper.toJsonString(initialInputData = inputData)
        // Then
        val expectedResult = SOME_JSON_INPUT.trimIndent().replace("\\s".toRegex(), "")
        assertEquals(expectedResult, jsonResult)
    }

    @Test
    fun `The toRobotInputJson() method should deserialize JSON String to RobotInputJson`() {
        // Given
        val inputData = SOME_JSON_INPUT
        // When
        val result = robotInputMapper.toRobotInputJson(inputData)
        // Then
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
        // Given
        val inputData = SOME_INVALID_JSON_INPUT
        // When
        val result = robotInputMapper.toRobotInputJson(inputData)
        // Then
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

        const val SOME_JSON_INPUT =
            """
            {
                "topRightCorner": {
                    "x": 5,
                    "y": 5
                },
                "roverPosition": {
                    "x": 1,
                    "y": 2
                },
                "roverDirection": "N",
                "movements": "LMLMLMLMM"
            }
            """

        const val SOME_INVALID_JSON_INPUT =
            """
            { "topRightCorner": "N", "roverDirection": }
            """
    }
}