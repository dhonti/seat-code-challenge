package com.dhontiveros.seatcodechallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dhontiveros.presentation.commons.extensions.configureEdgeToEdgeAppearance
import com.dhontiveros.presentation.ui.screen.MainScreen
import com.dhontiveros.presentation.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        configureEdgeToEdgeAppearance(isLightMode = true)
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                MainScreen(
                    state = state,
                    processIntent = viewModel::processIntent
                )
            }
        }
    }

}