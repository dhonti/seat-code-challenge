package com.dhontiveros.presentation.ui.viewmodel

import com.dhontiveros.commons.robot.toJsonString
import com.dhontiveros.domain.model.RobotDomainDirection
import com.dhontiveros.domain.model.RobotDomainErrorInput
import com.dhontiveros.domain.model.RobotDomainOutput
import com.dhontiveros.domain.model.RobotDomainResult
import com.dhontiveros.domain.usecase.CalculateRobotCoordinates
import com.dhontiveros.presentation.model.RobotInputUiModel
import com.dhontiveros.presentation.model.RobotResultUiModel
import com.dhontiveros.presentation.model.toDto
import com.squareup.moshi.Moshi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val moshi: Moshi = Moshi.Builder().build()
    private val calculateRobotCoordinates: CalculateRobotCoordinates = mockk()

    private val viewModel = MainViewModel(
        moshi = moshi,
        calculateRobotCoordinates = calculateRobotCoordinates
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given CalculateCoordinates intent, when success result, then update with response`() =
        runTest {
            val inputUiModel = buildInputUiModel()
            val jsonStringInputData = inputUiModel.toDto().toJsonString(moshi)
            val expectedResult = RobotDomainOutput(
                posX = 1,
                posY = 3,
                direction = RobotDomainDirection.North,
                movementsApplied = inputUiModel.movementsList.length.toLong()
            )
            val expectedMovements = inputUiModel.movementsList.length.toLong()

            coEvery { calculateRobotCoordinates(jsonStringInputData) } returns RobotDomainResult.Success(
                robotOutput = expectedResult,
            )

            viewModel.processIntent(intent = MainIntent.CalculateCoordinates(inputData = inputUiModel))
            advanceUntilIdle()

            val state = viewModel.state.value
            assertFalse(state.isLoading)
            assertNotNull(state.response)
            assertTrue(state.response is RobotResultUiModel)
            assertEquals(expectedResult.posX, state.response?.posX)
            assertEquals(expectedResult.posY, state.response?.posY)
            assertEquals(expectedResult.direction, state.response?.direction)
            assertEquals(expectedMovements, state.response?.appliedMovements)
            assertEquals(inputUiModel.movementsList.length.toLong(), state.response?.totalMovements)
            assertNull(state.error)
        }

    @Test
    fun `given CalculateCoordinates intent, when error result, then update with error`() = runTest {
        val inputUiModel = buildInputUiModel(movementsList = "INVALID")
        coEvery {
            calculateRobotCoordinates(
                inputUiModel.toDto().toJsonString(moshi)
            )
        } returns RobotDomainResult.Error(
            errorInput = RobotDomainErrorInput.Movements
        )

        viewModel.processIntent(intent = MainIntent.CalculateCoordinates(inputData = inputUiModel))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.response)
        assertNotNull(state.error)
        assertEquals(MainScreenErrorInput.MovementsList, state.error)
    }


    private companion object {
        fun buildInputUiModel(
            plateauSizeX: Long = 5,
            plateauSizeY: Long = 5,
            robotPosX: Long = 1,
            robotPosY: Long = 2,
            robotDirection: String = "N",
            movementsList: String = "LMLMLMLMM"
        ) = RobotInputUiModel(
            plateauSizeX = plateauSizeX,
            plateauSizeY = plateauSizeY,
            posX = robotPosX,
            posY = robotPosY,
            direction = robotDirection,
            movementsList = movementsList,
        )
    }
}