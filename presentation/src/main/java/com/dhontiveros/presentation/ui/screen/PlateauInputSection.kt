package com.dhontiveros.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dhontiveros.commons.ui.composables.AppPositiveIntInputField
import com.dhontiveros.presentation.R

@Composable
fun PlateauInputSection(
    modifier: Modifier = Modifier,
    onSizeChange: (String, String) -> Unit
) {
    var sizeX by rememberSaveable { mutableStateOf("") }
    var sizeY by rememberSaveable { mutableStateOf("") }

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
        Row(modifier = Modifier.fillMaxWidth()) {
            AppPositiveIntInputField(
                modifier = Modifier
                    .testTag(MainScreenTestTags.PLATEAU_INPUT_POS_X)
                    .weight(1f, false)
                    .padding(end = 4.dp),
                textId = R.string.main_screen_form_input_plateau_size_x,
                currentValue = sizeX,
                onValueChange = { sizeX = it },
            )
            AppPositiveIntInputField(
                modifier = Modifier
                    .testTag(MainScreenTestTags.PLATEAU_INPUT_POS_Y)
                    .weight(1f, false)
                    .padding(start = 4.dp),
                textId = R.string.main_screen_form_input_plateau_size_y,
                currentValue = sizeY,
                onValueChange = { sizeY = it },
            )
        }
    }
}