package com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dhontiveros.seatcodechallenge.domain.InputData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

}

data class MainViewState(
    val isLoading: Boolean = false,
    val response: String? = null,
)

sealed class MainIntent {
    data class CalculateCoordinates(val inputData: InputData)
}
