package com.dhontiveros.presentation.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dhontiveros.commons.ui.composables.AppButton
import com.dhontiveros.commons.ui.composables.AppPositiveIntInputField
import com.dhontiveros.presentation.R

@Composable
fun RobotInputSection(
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
        Row(modifier = Modifier.fillMaxWidth()) {
            AppPositiveIntInputField(
                modifier = Modifier
                    .testTag(MainScreenTestTags.ROBOT_INPUT_POS_X)
                    .weight(1f, false)
                    .padding(end = 4.dp),
                textId = R.string.main_screen_form_input_robot_pos_x,
                currentValue = posX,
                onValueChange = { posX = it },
            )
            AppPositiveIntInputField(
                modifier = Modifier
                    .testTag(MainScreenTestTags.ROBOT_INPUT_POS_Y)
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
        MovementsInputFiled(
            modifier = Modifier
                .testTag(MainScreenTestTags.ROBOT_INPUT_MOVEMENTS)
                .fillMaxWidth(),
            textId = R.string.main_screen_form_input_robot_movements,
            value = movements,
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
    Text(text = stringResource(R.string.main_screen_form_input_robot_direction))
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        directions.forEach { dir ->
            val isSelected = selected == dir
            AppButton(
                modifier = Modifier
                    .testTag(
                        String.format(
                            MainScreenTestTags.ROBOT_INPUT_DIRECTION,
                            dir
                        )
                    )
                    .weight(1f),
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
fun MovementsInputFiled(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes textId: Int,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { input ->
            val filtered = input.uppercase().filter { it == 'L' || it == 'R' || it == 'M' }
            onValueChange(filtered)
        },
        label = { Text(text = stringResource(textId)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Characters
        ),
    )
}