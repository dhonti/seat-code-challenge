package com.dhontiveros.seatcodechallenge.presentation.rule

import android.provider.Settings.Global.ANIMATOR_DURATION_SCALE
import android.provider.Settings.Global.TRANSITION_ANIMATION_SCALE
import android.provider.Settings.Global.WINDOW_ANIMATION_SCALE
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice.getInstance
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.IOException

class DisableAnimationsRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                disableAnimations()
                try {
                    base.evaluate()
                } finally {
                    enableAnimations()
                }
            }
        }

    @Throws(IOException::class)
    private fun disableAnimations() {
        with(getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand("settings put global $TRANSITION_ANIMATION_SCALE 0")
            executeShellCommand("settings put global $ANIMATOR_DURATION_SCALE 0")
            executeShellCommand("settings put global $WINDOW_ANIMATION_SCALE 0")
        }
    }

    @Throws(IOException::class)
    private fun enableAnimations() {
        with(getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand("settings put global $TRANSITION_ANIMATION_SCALE 1")
            executeShellCommand("settings put global $ANIMATOR_DURATION_SCALE 1")
            executeShellCommand("settings put global $WINDOW_ANIMATION_SCALE 1")
        }
    }
}