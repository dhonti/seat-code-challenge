package com.dhontiveros.presentation.commons.extensions

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import com.dhontiveros.presentation.R
import com.dhontiveros.presentation.ui.viewmodel.MainScreenErrorInput

fun ComponentActivity.configureEdgeToEdgeAppearance(isLightMode: Boolean) {
    val systemBarStyle = if (isLightMode) {
        SystemBarStyle.light(
            android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
        )
    } else {
        SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
    }
    enableEdgeToEdge(statusBarStyle = systemBarStyle, navigationBarStyle = systemBarStyle)
}

fun MainScreenErrorInput.toTitleStringResId(): Int =
    when (this) {
        is MainScreenErrorInput.PlateauSize -> R.string.main_screen_error_plateau_size_title
        is MainScreenErrorInput.StartPosition -> R.string.main_screen_error_start_position_title
        is MainScreenErrorInput.MovementsList -> R.string.main_screen_error_movements_title
    }

fun MainScreenErrorInput.toBodyStringResId(): Int =
    when (this) {
        is MainScreenErrorInput.PlateauSize -> R.string.main_screen_error_plateau_size_body
        is MainScreenErrorInput.StartPosition -> R.string.main_screen_error_start_position_body
        is MainScreenErrorInput.MovementsList -> R.string.main_screen_error_movements_body
    }