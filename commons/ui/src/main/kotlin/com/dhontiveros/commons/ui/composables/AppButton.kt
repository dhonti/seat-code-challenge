package com.dhontiveros.commons.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    Button(
        modifier = modifier,
        enabled = isEnabled,
        onClick = onClick,
        colors = colors
    ) {
        Text(text = text)
    }
}

private const val MAX_TIME_FADE_ANIM = 300
private const val BUTTON_TARGET_ALPHA_BUTTON = 0.6f

@Composable
fun AppLoadingButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    textLoading: String,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    Button(
        modifier = modifier,
        onClick = { if (!isLoading) onClick() },
        enabled = isEnabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = BUTTON_TARGET_ALPHA_BUTTON),
            disabledContentColor = Color.White.copy(alpha = BUTTON_TARGET_ALPHA_BUTTON)
        )
    ) {
        AnimatedContent(
            targetState = isLoading,
            label = "loading-button-content",
            transitionSpec = { fadeIn(tween(MAX_TIME_FADE_ANIM)) togetherWith fadeOut(tween(MAX_TIME_FADE_ANIM)) }
        ) { loading ->
            if (loading) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = textLoading)
                }
            } else {
                Text(text = text)
            }
        }
    }
}