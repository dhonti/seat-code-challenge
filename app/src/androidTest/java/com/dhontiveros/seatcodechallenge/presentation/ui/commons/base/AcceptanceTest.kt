package com.dhontiveros.seatcodechallenge.presentation.ui.commons.base

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import com.dhontiveros.seatcodechallenge.presentation.rule.DisableAnimationsRule
import com.dhontiveros.seatcodechallenge.presentation.ui.MainActivity
import com.dhontiveros.seatcodechallenge.presentation.ui.screen.MainScreen
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule

abstract class AcceptanceTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val disposableAnimations = DisableAnimationsRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    fun onMainScreen(): MainScreen {
        ActivityScenario.launch(MainActivity::class.java)
        return MainScreen(composeTestRule)
    }

    fun closeKeyBoard() {
        Espresso.closeSoftKeyboard()
    }

}