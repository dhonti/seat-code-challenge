package com.dhontiveros.presentation.commons.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AppInputField(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    currentValue: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        modifier = modifier,
        value = currentValue,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(textId)) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
    )
}