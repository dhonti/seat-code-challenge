package com.dhontiveros.seatcodechallenge.ui.commons.base

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import com.dhontiveros.seatcodechallenge.MainActivity
import com.dhontiveros.presentation.ui.screen.MainScreen
import com.dhontiveros.seatcodechallenge.rule.DisableAnimationsRule
import com.dhontiveros.seatcodechallenge.ui.screen.MainScreen
import com.karumi.shot.ScreenshotTest
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule

abstract class AcceptanceTest : ScreenshotTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val disposableAnimations = DisableAnimationsRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    fun onMainScreen(): MainScreen {
        ActivityScenario.launch(MainActivity::class.java)
        return MainScreen(composeRule)
    }

    fun closeKeyBoard() {
        Espresso.closeSoftKeyboard()
    }

    fun compareScreenshot(name: String? = null) {
        compareScreenshot(composeRule, name)
    }

}