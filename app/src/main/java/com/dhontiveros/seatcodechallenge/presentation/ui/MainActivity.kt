package com.dhontiveros.seatcodechallenge.presentation.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.dhontiveros.seatcodechallenge.presentation.extensions.configureEdgeToEdgeAppearance
import com.dhontiveros.seatcodechallenge.presentation.ui.screen.MainScreen
import com.dhontiveros.seatcodechallenge.presentation.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        configureEdgeToEdgeAppearance(isLightMode = true)
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }

}