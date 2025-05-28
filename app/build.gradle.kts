plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.shot)
}

android {
    namespace = "com.dhontiveros.seatcodechallenge"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "com.dhontiveros.seatcodechallenge"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.dhontiveros.seatcodechallenge.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_18.majorVersion
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.core.splashscreen)

    // Dagger
    implementation(libs.hilt.android)
    implementation(libs.androidx.activity)
    ksp(libs.hilt.compiler)

    // Compose BoM
    // ----------------------------------
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)

    // Compose libraries:
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.runtime)

    implementation(libs.compose.activity)
    implementation(libs.compose.viewmodel)
    // ----------------------------------

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Moshi
    implementation(libs.moshi)
    ksp(libs.moshi.kotlin.codegen)

    // TESTING
    // ----------------------------------

    // JUnit
    testImplementation(libs.junit)

    // Mockito:
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    // Mockk:
    testImplementation(libs.mockk)

    // KotlinX Coroutines
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // UI Automator (used to create TestRule
    androidTestImplementation(libs.androidx.uiautomator)

    // UI testing:
    // Compose
    androidTestImplementation(libs.compose.ui.test.junit)
    debugImplementation(libs.compose.ui.test.manifest)

    // Shot
    androidTestImplementation(libs.shot)

    // Hilt:
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)
}

shot {
    applicationId = "com.dhontiveros.seatcodechallenge"
}
