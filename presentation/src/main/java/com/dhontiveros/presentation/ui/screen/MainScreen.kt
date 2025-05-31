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
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
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
    var formState by rememberSaveable(stateSaver = RobotFormStateSaver) {
        mutableStateOf(RobotFormState())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
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
    var plateauSizeX by rememberSaveable { mutableStateOf("") }
    var plateauSizeY by rememberSaveable { mutableStateOf("") }

    var posX by rememberSaveable { mutableStateOf("") }
    var posY by rememberSaveable { mutableStateOf("") }
    var direction by rememberSaveable { mutableStateOf("N") }
    var movements by rememberSaveable { mutableStateOf("") }

    val wasModified by rememberSaveable { mutableStateOf(false) }

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
        if (wasModified) {
            processIntent(MainIntent.ResetOutput)
        }
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

val RobotFormStateSaver = listSaver(
    save = {
        listOf(
            it.posX,
            it.posY,
            it.direction,
            it.plateauSizeX,
            it.plateauSizeY,
            it.movements
        )
    },
    restore = { RobotFormState(it[0], it[1], it[2], it[3], it[4], it[5]) }
)