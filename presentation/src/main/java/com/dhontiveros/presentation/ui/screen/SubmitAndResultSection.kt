package com.dhontiveros.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dhontiveros.commons.ui.composables.AppFadeSection
import com.dhontiveros.commons.ui.composables.AppLoadingButton
import com.dhontiveros.presentation.R
import com.dhontiveros.presentation.commons.extensions.toBodyStringResId
import com.dhontiveros.presentation.commons.extensions.toTitleStringResId
import com.dhontiveros.presentation.model.RobotInputUiModel
import com.dhontiveros.presentation.ui.viewmodel.MainIntent
import com.dhontiveros.presentation.ui.viewmodel.MainViewState

@Composable
fun SubmitAndResultSection(
    state: MainViewState,
    formState: RobotFormState,
    processIntent: (MainIntent) -> Unit
) {
    AppFadeSection(visible = state.response != null) {
        state.response?.let {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .testTag(MainScreenTestTags.ROBOT_RESULT_POSITION)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.main_screen_result_position, it.toString())
                )
                Text(
                    modifier = Modifier
                        .testTag(MainScreenTestTags.ROBOT_RESULT_MOVEMENTS)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(
                        R.string.main_screen_result_movements,
                        it.totalMovements,
                        it.appliedMovements
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    AppFadeSection(visible = state.error != null) {
        state.error?.let {
            ResultErrorSection(
                modifier = Modifier.padding(bottom = 16.dp),
                titleId = it.toTitleStringResId(),
                bodyId = it.toBodyStringResId()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
    AppLoadingButton(
        modifier = Modifier
            .testTag(MainScreenTestTags.ROBOT_SUBMIT_FORM_BUTTON)
            .fillMaxWidth(),
        text = stringResource(R.string.main_screen_calculate_button),
        textLoading = stringResource(R.string.main_screen_calculate_button_loading),
        isEnabled = formState.isCompleted,
        isLoading = state.isLoading,
        onClick = {
            processIntent(
                MainIntent.CalculateCoordinates(
                    inputData = RobotInputUiModel(
                        posX = formState.posX.toLongOrNull() ?: 0L,
                        posY = formState.posY.toLongOrNull() ?: 0L,
                        direction = formState.direction,
                        plateauSizeX = formState.plateauSizeX.toLongOrNull() ?: 0L,
                        plateauSizeY = formState.plateauSizeY.toLongOrNull() ?: 0L,
                        movementsList = formState.movements
                    )
                )
            )
        }
    )
}