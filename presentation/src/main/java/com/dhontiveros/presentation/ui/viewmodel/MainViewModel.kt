package com.dhontiveros.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhontiveros.commons.robot.toJsonString
import com.dhontiveros.domain.model.RobotDomainErrorInput
import com.dhontiveros.domain.model.RobotDomainResult
import com.dhontiveros.domain.usecase.CalculateRobotCoordinates
import com.dhontiveros.presentation.model.RobotInputUiModel
import com.dhontiveros.presentation.model.RobotResultUiModel
import com.dhontiveros.presentation.model.toDto
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moshi: Moshi,
    private val calculateRobotCoordinates: CalculateRobotCoordinates
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState> = _state

    fun processIntent(intent: MainIntent) {
        if (intent is MainIntent.CalculateCoordinates) {
            submit(inputUiModel = intent.inputData)
        }
    }

    private fun submit(inputUiModel: RobotInputUiModel) {
        updateState { it.isLoading = true }
        viewModelScope.launch {
            when (val result =
                calculateRobotCoordinates(jsonInput = inputUiModel.toDto().toJsonString(moshi))) {
                is RobotDomainResult.Success -> {
                    updateState {
                        with(result) {
                            it.response = RobotResultUiModel(
                                posX = robotOutput.posX,
                                posY = robotOutput.posY,
                                direction = robotOutput.direction,
                                totalMovements = inputUiModel.movementsList.length.toLong(),
                                appliedMovements = robotOutput.movementsApplied
                            )
                        }
                        it.isLoading = false
                    }
                }

                is RobotDomainResult.Error -> {
                    updateState {
                        it.error = result.errorInput.toMainScreenError()
                        it.isLoading = false
                    }
                }
            }
        }
    }

    private fun updateState(transformation: (MainViewState) -> Unit) {
        val deepCopy = _state.value.copy()
        transformation(deepCopy)
        _state.value = deepCopy
    }
}

data class MainViewState(
    var isLoading: Boolean = false,
    var response: RobotResultUiModel? = null,
    var error: MainScreenErrorInput? = null
)

sealed class MainIntent {
    data class CalculateCoordinates(val inputData: RobotInputUiModel) : MainIntent()
}

sealed class MainScreenErrorInput {
    data object PlateauSize : MainScreenErrorInput()
    data object StartPosition : MainScreenErrorInput()
    data object MovementsList : MainScreenErrorInput()
}


private fun RobotDomainErrorInput.toMainScreenError(): MainScreenErrorInput? = when (this) {
    RobotDomainErrorInput.PlateauSize -> MainScreenErrorInput.PlateauSize
    RobotDomainErrorInput.StartPosition -> MainScreenErrorInput.StartPosition
    RobotDomainErrorInput.Movements -> MainScreenErrorInput.MovementsList
    else -> null
}
