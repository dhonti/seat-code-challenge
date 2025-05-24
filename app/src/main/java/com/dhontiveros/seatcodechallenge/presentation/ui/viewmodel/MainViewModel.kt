package com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhontiveros.seatcodechallenge.domain.model.InputData
import com.dhontiveros.seatcodechallenge.domain.model.ResultMovementRobot
import com.dhontiveros.seatcodechallenge.domain.usecase.CalculateCoordinates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val calculateCoordinates: CalculateCoordinates
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state

    fun processIntent(intent: MainIntent) {
        if (intent is MainIntent.CalculateCoordinates) {
            submit(inputData = intent.inputData)
        }
    }

    private fun submit(inputData: InputData) {
        updateState { it.isLoading = true }
        viewModelScope.launch {
            val result = calculateCoordinates(inputData = inputData)
            when( result ){
                is ResultMovementRobot.Success -> {
                    updateState { it.response = result.position.toString() }
                }
                is ResultMovementRobot.Error -> {

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
)

sealed class MainIntent {
    data class CalculateCoordinates(val inputData: InputData) : MainIntent()
}

sealed class MainScreenErrorInput {
    data object PlateauSize : MainScreenErrorInput()
    data object StartPosition : MainScreenErrorInput()
}
