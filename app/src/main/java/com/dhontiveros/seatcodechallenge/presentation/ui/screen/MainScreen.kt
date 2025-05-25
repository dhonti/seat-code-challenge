package com.dhontiveros.seatcodechallenge.presentation.ui.screen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dhontiveros.seatcodechallenge.R
import com.dhontiveros.seatcodechallenge.presentation.ui.composables.AppButton
import com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel.InitialInputData
import com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel.MainIntent
import com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel.MainViewState

@Composable
fun MainScreen(
    state: MainViewState,
    processIntent: (MainIntent) -> Unit
) {
    Scaffold { padding ->
        MainBody(
            modifier = Modifier.padding(padding),
            state = state,
            processIntent = processIntent
        )
    }
}

@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    state: MainViewState,
    processIntent: (MainIntent) -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier
                .weight(1f, false)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InputSection(state = state, processIntent = processIntent)
            ResultSection(state = state)
        }
        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
    }
}

@Composable
fun InputSection(state: MainViewState, processIntent: (MainIntent) -> Unit) {
    Text("this is a sample")
    AppButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.main_screen_calculate_button),
        isEnabled = !state.isLoading,
        onClick = {
            processIntent(
                MainIntent.CalculateCoordinates(
                    inputData = InitialInputData(
                        posX = 1,
                        posY = 2,
                        direction = "N",
                        plateauSizeX = 5,
                        plateauSizeY = 5,
                        movementsList = "LMLMLMLMM"
                    )
                )
            )
        }
    )
}

@Composable
fun ResultSection(state: MainViewState) {
    state.response?.let {
        Text("Result: $it")
    }
}