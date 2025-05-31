package com.dhontiveros.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.dhontiveros.presentation.ui.viewmodel.MainIntent
import com.dhontiveros.presentation.ui.viewmodel.MainViewState

@Composable
fun MainScreen(
    state: MainViewState,
    processIntent: (MainIntent) -> Unit
) {
    Scaffold(modifier = Modifier.testTag(MainScreenTestTags.SCREEN)) { padding ->
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
    var formState by remember { mutableStateOf(RobotFormState()) }

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
            RobotInputForm(
                processIntent = processIntent,
                onFormStateChange = { formState = it }
            )
        }
        SubmitAndResultSection(
            state = state,
            formState = formState,
            processIntent = processIntent
        )
    }
}

@Composable
private fun RobotInputForm(
    onFormStateChange: (RobotFormState) -> Unit,
    processIntent: (MainIntent) -> Unit
) {
    var plateauSizeX by remember { mutableStateOf("") }
    var plateauSizeY by remember { mutableStateOf("") }

    var posX by remember { mutableStateOf("") }
    var posY by remember { mutableStateOf("") }
    var direction by remember { mutableStateOf("N") }
    var movements by remember { mutableStateOf("") }

    // Emit when some filed is changed
    LaunchedEffect(plateauSizeX, plateauSizeY, posX, posY, direction, movements) {
        onFormStateChange(
            RobotFormState(
                posX = posX,
                posY = posY,
                direction = direction,
                plateauSizeX = plateauSizeX,
                plateauSizeY = plateauSizeY,
                movements = movements
            )
        )
        processIntent(MainIntent.ResetOutput)
    }

    Spacer(modifier = Modifier.height(8.dp))
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


data class RobotFormState(
    val posX: String = "",
    val posY: String = "",
    val direction: String = "N",
    val plateauSizeX: String = "",
    val plateauSizeY: String = "",
    val movements: String = ""
) {
    val isCompleted: Boolean
        get() = listOf(
            posX,
            posY,
            direction,
            plateauSizeX,
            plateauSizeY,
            movements
        ).all { it.isNotBlank() }
}