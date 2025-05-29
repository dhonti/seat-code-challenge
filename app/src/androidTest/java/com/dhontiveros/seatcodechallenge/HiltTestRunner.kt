package com.dhontiveros.seatcodechallenge

import android.app.Application
import android.content.Context
import com.karumi.shot.ShotTestRunner
import dagger.hilt.android.testing.CustomTestApplication

class HiltTestRunner : ShotTestRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(
            cl,
            HiltAndroidApplication_Application::class.java.name,
            context
        )
    }
}

@CustomTestApplication(Application::class)
interface HiltAndroidApplication