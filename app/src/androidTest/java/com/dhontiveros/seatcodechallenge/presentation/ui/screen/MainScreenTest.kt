package com.dhontiveros.seatcodechallenge.presentation.ui.screen

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dhontiveros.seatcodechallenge.presentation.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
class MainScreenTest {
    
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    
    @Before
    fun setUp() {
        hiltRule.inject()
    }
    
}