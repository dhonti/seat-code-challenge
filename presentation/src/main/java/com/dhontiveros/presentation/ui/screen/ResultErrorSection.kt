package com.dhontiveros.presentation.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ResultErrorSection(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int,
    @StringRes bodyId: Int
) {
    Column(
        modifier = modifier
            .testTag(MainScreenTestTags.ROBOT_RESULT_ERROR_SECTION)
            .clip(RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.errorContainer)
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .testTag(MainScreenTestTags.ROBOT_RESULT_ERROR_TITLE)
                .fillMaxWidth(),
            text = stringResource(titleId),
            color = MaterialTheme.colorScheme.error
        )
        Text(
            modifier = Modifier
                .testTag(MainScreenTestTags.ROBOT_RESULT_ERROR_BODY)
                .fillMaxWidth(),
            text = stringResource(bodyId)
        )
    }
}