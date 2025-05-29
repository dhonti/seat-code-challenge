package com.dhontiveros.presentation.ui.commons.base

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.dhontiveros.presentation.ui.commons.extensions.waitForNodeWithTagDisplayed

open class BaseScreen(
    composeTestRule: ComposeTestRule,
    screenTestTag: String
) {
    init {
        composeTestRule.waitForNodeWithTagDisplayed(screenTestTag)
    }
}