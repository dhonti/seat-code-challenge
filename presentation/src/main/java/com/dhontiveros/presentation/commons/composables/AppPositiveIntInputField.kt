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
fun AppPositiveIntInputField(
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    currentValue: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = currentValue,
        onValueChange = { input ->
            val filtered = input.filter { it.isDigit() }
            val sanitized = when {
                filtered.isEmpty() -> ""
                filtered.startsWith("0") && filtered.length > 1 -> filtered.trimStart('0')
                else -> filtered
            }
            onValueChange(sanitized)
        },
        label = { Text(text = stringResource(textId)) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}
