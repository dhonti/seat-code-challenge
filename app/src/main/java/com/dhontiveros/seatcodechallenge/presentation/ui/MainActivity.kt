package com.dhontiveros.seatcodechallenge.presentation.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.dhontiveros.seatcodechallenge.presentation.ui.extensions.configureEdgeToEdgeAppearance
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        configureEdgeToEdgeAppearance(isLightMode = true)
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            MaterialTheme {

            }
        }
    }

}