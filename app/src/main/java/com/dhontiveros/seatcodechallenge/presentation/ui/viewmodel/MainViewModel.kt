package com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhontiveros.seatcodechallenge.domain.model.ErrorInputRobot
import com.dhontiveros.seatcodechallenge.domain.model.ResultMovementRobot
import com.dhontiveros.seatcodechallenge.domain.model.RobotInputData
import com.dhontiveros.seatcodechallenge.domain.usecase.CalculateRobotCoordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val calculateRobotCoordinates: CalculateRobotCoordinates
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state

    fun processIntent(intent: MainIntent) {
        if (intent is MainIntent.CalculateCoordinates) {
            submit(robotInputData = intent.robotInputData)
        }
    }

    private fun submit(robotInputData: RobotInputData) {
        updateState { it.isLoading = true }
        viewModelScope.launch {
            when (val result = calculateRobotCoordinates(robotInputData = robotInputData)) {
                is ResultMovementRobot.Success -> {
                    updateState { it.response = result.robotPosition.toString() }
                }

                is ResultMovementRobot.Error -> {
                    updateState { it.error = result.typeErrorInput.toMainScreenError() }
                }
            }
        }
    }

    private fun updateState(transformation: (MainViewState) -> Unit) {
        val deepCopy = _state.value
        transformation(deepCopy)
        _state.value = deepCopy
    }
}

data class MainViewState(
    var isLoading: Boolean = false,
    var response: String? = null,
    var error: MainScreenErrorInput? = null
)

sealed class MainIntent {
    data class CalculateCoordinates(val robotInputData: RobotInputData) : MainIntent()
}

sealed class MainScreenErrorInput {
    data object PlateauSize : MainScreenErrorInput()
    data object StartPosition : MainScreenErrorInput()
    data object MovementsList : MainScreenErrorInput()
}


private fun ErrorInputRobot.toMainScreenError(): MainScreenErrorInput? = when (this) {
    ErrorInputRobot.PlateauSize -> MainScreenErrorInput.PlateauSize
    ErrorInputRobot.StartPosition -> MainScreenErrorInput.StartPosition
    ErrorInputRobot.Movements -> MainScreenErrorInput.MovementsList
    else -> null
}