package com.dhontiveros.seatcodechallenge.presentation.viewmodel

import com.dhontiveros.rover_robot.model.helpers.RobotDirection
import com.dhontiveros.rover_robot.model.helpers.RobotPosition
import com.dhontiveros.rover_robot.processor.ErrorInputRobot
import com.dhontiveros.rover_robot.processor.RobotResult
import com.dhontiveros.domain.usecase.CalculateRobotCoordinates
import com.dhontiveros.presentation.ui.viewmodel.InitialInputData
import com.dhontiveros.presentation.ui.viewmodel.MainIntent
import com.dhontiveros.presentation.ui.viewmodel.MainScreenErrorInput
import com.dhontiveros.presentation.ui.viewmodel.MainViewModel
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val calculateRobotCoordinates: CalculateRobotCoordinates = mockk()
    private val viewModel = MainViewModel(calculateRobotCoordinates)

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
            val inputData = buildInputData()
            val expectedPosition =
                RobotPosition(x = 1, y = 1, direction = RobotDirection.North)
            val expectedMovements = inputData.movementsList.length.toLong()

            coEvery { calculateRobotCoordinates(inputData) } returns RobotResult.Success(
                robotPosition = expectedPosition,
                movementsApplied = expectedMovements
            )

            viewModel.processIntent(intent = MainIntent.CalculateCoordinates(inputData))
            advanceUntilIdle()

            val state = viewModel.state.value
            assertFalse(state.isLoading)
            assertNotNull(state.response)
            assertEquals(expectedPosition, state.response?.finalPosition)
            assertEquals(expectedMovements, state.response?.appliedMovements)
            assertEquals(inputData.movementsList.length.toLong(), state.response?.totalMovements)
            assertNull(state.error)
        }

    @Test
    fun `given CalculateCoordinates intent, when error result, then update with error`() = runTest {
        val inputData = buildInputData(movementsList = "INVALID")
        coEvery { calculateRobotCoordinates(inputData) } returns RobotResult.Error(
            errorInput = ErrorInputRobot.Movements
        )

        viewModel.processIntent(intent = MainIntent.CalculateCoordinates(inputData = inputData))
        advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.response)
        assertNotNull(state.error)
        assertEquals(MainScreenErrorInput.MovementsList, state.error)
    }


    private companion object {
        fun buildInputData(
            plateauSizeX: Long = 5,
            plateauSizeY: Long = 5,
            robotPosX: Long = 1,
            robotPosY: Long = 2,
            robotDirection: String = "N",
            movementsList: String = "LMLMLMLMM"
        ) = InitialInputData(
            plateauSizeX = plateauSizeX,
            plateauSizeY = plateauSizeY,
            posX = robotPosX,
            posY = robotPosY,
            direction = robotDirection,
            movementsList = movementsList
        )
    }
}