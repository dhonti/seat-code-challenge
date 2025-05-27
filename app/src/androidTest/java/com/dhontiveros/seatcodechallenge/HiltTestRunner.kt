package com.dhontiveros.seatcodechallenge

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.CustomTestApplication

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltAndroidApplication_Application::class.java.name, context)
    }
}

@CustomTestApplication(Application::class)
interface HiltAndroidApplication