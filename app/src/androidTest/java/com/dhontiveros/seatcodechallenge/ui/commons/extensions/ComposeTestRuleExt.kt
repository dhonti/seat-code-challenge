package com.dhontiveros.seatcodechallenge.ui.commons.extensions

import android.util.Log
import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText

fun ComposeTestRule.waitForNodeWithTagDisplayed(
    tag: String,
    useUnmergedTree: Boolean = false,
): SemanticsNodeInteraction =
    waitForNodeDisplayed {
        Log.d(
            "ComposeTestRule",
            "waitForNodeWithTagDisplayed: tag = $tag, useUnmergedTree = $useUnmergedTree"
        )
        it.onNodeWithTag(tag, useUnmergedTree)
    }

fun ComposeTestRule.waitForNodeWithTextDisplayed(
    text: String,
    useUnmergedTree: Boolean = false,
): SemanticsNodeInteraction =
    waitForNodeDisplayed {
        Log.d("ComposeTestRule", "waitForNodeWithTagDisplayed: text = $text, useUnmergedTree = $useUnmergedTree")
        it.onNodeWithText(text, useUnmergedTree)
    }

fun ComposeTestRule.waitForNodeDisplayed(
    fetchNode: (SemanticsNodeInteractionsProvider) -> SemanticsNodeInteraction
): SemanticsNodeInteraction =
    waitForNodeAssertion(fetchNode) {
        it.assertIsDisplayed()
    }

fun ComposeTestRule.waitForNodeAssertion(
    fetchNode: (SemanticsNodeInteractionsProvider) -> SemanticsNodeInteraction,
    assertion: (SemanticsNodeInteraction) -> SemanticsNodeInteraction,
): SemanticsNodeInteraction =
    waitForAssertion { assertion(fetchNode(this)) }

@Throws(AssertionError::class, ComposeTimeoutException::class)
private fun ComposeTestRule.waitForAssertion(
    assertion: () -> SemanticsNodeInteraction
): SemanticsNodeInteraction {
    var assertionError: AssertionError? = null
    var result: SemanticsNodeInteraction? = null
    waitForIdle()
    try {
        waitUntil(WAITING_TIMEOUT) {
            try {
                result = assertion()
                true
            } catch (e: AssertionError) {
                assertionError = e
                false
            }
        }
    } catch (timeoutException: ComposeTimeoutException) {
        if (assertionError == null) {
            throw timeoutException
        }
    }
    if (result == null) {
        throw AssertionError("Assertion not satisfied on time", assertionError)
    }
    return result!!
}

private const val WAITING_TIMEOUT = 10_000L