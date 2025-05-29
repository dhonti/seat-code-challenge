plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = Config.applicationId
    compileSdk = Config.targetSdk

    defaultConfig {
        minSdk = Config.minSdk

        testInstrumentationRunner = Config.testInstrumentationRunner
        consumerProguardFiles(Config.consumerRules)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(Config.proguardFile),
                Config.proguardRules
            )
        }
    }
    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {
    modules(listOf(
        Dependencies.Modules.commonsUi,
        Dependencies.Modules.domain,
        Dependencies.Modules.commonsRobot,
    ))

    // Android libs:
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)

    // Dagger
    implementation(libs.hilt.android)
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

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // TESTING
    // ###############################################
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