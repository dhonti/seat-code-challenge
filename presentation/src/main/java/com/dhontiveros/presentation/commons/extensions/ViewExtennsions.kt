package com.dhontiveros.presentation.commons.extensions

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge

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