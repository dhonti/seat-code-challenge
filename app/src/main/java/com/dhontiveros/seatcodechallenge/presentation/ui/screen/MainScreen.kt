package com.dhontiveros.seatcodechallenge.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dhontiveros.seatcodechallenge.R
import com.dhontiveros.seatcodechallenge.presentation.ui.composables.AppButton
import com.dhontiveros.seatcodechallenge.presentation.ui.composables.AppInputField
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
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
    var plateauSizeX by remember { mutableStateOf("") }
    var plateauSizeY by remember { mutableStateOf("") }

    PlateauDataInput(
        onSizeChange = { x, y ->
            plateauSizeX = x
            plateauSizeY = y
        }
    )

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
fun PlateauDataInput(onSizeChange: (String, String) -> Unit) {
    var sizeX by remember { mutableStateOf("") }
    var sizeY by remember { mutableStateOf("") }

    LaunchedEffect(sizeX, sizeY) {
        onSizeChange(sizeX, sizeY)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Gray),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.main_screen_form_input_plateau_title))
        Text(text = stringResource(R.string.main_screen_form_input_plateau_body))
        Row(modifier = Modifier.fillMaxWidth()) {
            AppInputField(
                modifier = Modifier
                    .weight(1f, false)
                    .padding(end = 4.dp),
                textId = R.string.main_screen_form_input_plateau_size_x,
                currentValue = sizeX,
                onValueChange = { sizeX = it },
            )
            AppInputField(
                modifier = Modifier
                    .weight(1f, false)
                    .padding(start = 4.dp),
                textId = R.string.main_screen_form_input_plateau_size_y,
                currentValue = sizeY,
                onValueChange = { sizeY = it },
            )
        }
    }
}

@Composable
fun RobotDataInput() {

}

@Composable
fun ResultSection(state: MainViewState) {
    state.response?.let {
        Text("Result: $it")
    }
}