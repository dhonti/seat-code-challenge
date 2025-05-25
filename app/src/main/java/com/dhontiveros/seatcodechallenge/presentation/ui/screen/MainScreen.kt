package com.dhontiveros.seatcodechallenge.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.dhontiveros.seatcodechallenge.presentation.commons.composables.AppButton
import com.dhontiveros.seatcodechallenge.presentation.commons.composables.AppInputField
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
private fun MainBody(
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
                .weight(1f, true)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RobotInputForm(state = state)
        }
        SubmitAndResultSection(state = state, processIntent = processIntent)
    }
}

@Composable
private fun RobotInputForm(state: MainViewState) {
    var plateauSizeX by remember { mutableStateOf("") }
    var plateauSizeY by remember { mutableStateOf("") }

    var posX by remember { mutableStateOf("") }
    var posY by remember { mutableStateOf("") }
    var direction by remember { mutableStateOf("N") }
    var movements by remember { mutableStateOf("") }

    PlateauInputSection(
        modifier = Modifier.fillMaxWidth(),
        onSizeChange = { x, y ->
            plateauSizeX = x
            plateauSizeY = y
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    RobotInputSection(
        modifier = Modifier.fillMaxWidth(),
        onRobotChange = { x, y, dir, mov ->
            posX = x
            posY = y
            direction = dir
            movements = mov
        }
    )
}

@Composable
private fun PlateauInputSection(
    modifier: Modifier = Modifier,
    onSizeChange: (String, String) -> Unit
) {
    var sizeX by remember { mutableStateOf("") }
    var sizeY by remember { mutableStateOf("") }

    LaunchedEffect(sizeX, sizeY) {
        onSizeChange(sizeX, sizeY)
    }

    Column(
        modifier = modifier
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
private fun RobotInputSection(
    modifier: Modifier = Modifier,
    onRobotChange: (String, String, String, String) -> Unit
) {
    var posX by remember { mutableStateOf("") }
    var posY by remember { mutableStateOf("") }
    var direction by remember { mutableStateOf("N") }
    var movements by remember { mutableStateOf("") }

    LaunchedEffect(posX, posY, direction, movements) {
        onRobotChange(posX, posY, direction, movements)
    }

    Column(
        modifier = modifier
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Gray),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.main_screen_form_input_robot_title))
        Text(text = stringResource(R.string.main_screen_form_input_robot_body))
        Row(modifier = Modifier.fillMaxWidth()) {
            AppInputField(
                modifier = Modifier
                    .weight(1f, false)
                    .padding(end = 4.dp),
                textId = R.string.main_screen_form_input_robot_pos_x,
                currentValue = posX,
                onValueChange = { posX = it },
            )
            AppInputField(
                modifier = Modifier
                    .weight(1f, false)
                    .padding(start = 4.dp),
                textId = R.string.main_screen_form_input_robot_pos_y,
                currentValue = posY,
                onValueChange = { posY = it },
            )

        }
        Spacer(Modifier.height(4.dp))
        DirectionToggleGroup(
            selected = direction,
            onSelectedDirection = { direction = it }
        )
        Spacer(Modifier.height(4.dp))
        AppInputField(
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.main_screen_form_input_robot_movements,
            currentValue = movements,
            onValueChange = { movements = it },
        )
    }
}

@Composable
private fun DirectionToggleGroup(
    selected: String,
    onSelectedDirection: (String) -> Unit
) {
    val directions = listOf("N", "S", "E", "W")
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        directions.forEach { dir ->
            val isSelected = selected == dir
            AppButton(
                modifier = Modifier.weight(1f),
                text = dir,
                onClick = { onSelectedDirection(dir) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceContainerHigh
                )
            )
        }
    }
}

@Composable
private fun SubmitAndResultSection(state: MainViewState, processIntent: (MainIntent) -> Unit) {
    state.response?.let {
        Text("Result: $it")
    }
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