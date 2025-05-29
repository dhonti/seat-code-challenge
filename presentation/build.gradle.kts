plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.compose.compiler)

    // SOLO si haces tests instrumentados con Hilt:
    id("dagger.hilt.android.plugin")

//    alias(libs.plugins.shot)
}

android {
    namespace = "com.dhontiveros.presentation"
    compileSdk = Config.targetSdk

    defaultConfig {
        minSdk = Config.minSdk

//        testInstrumentationRunner = Config.testInstrumentationRunner
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.kotlin.compiler.get()
    }

    buildFeatures {
        compose = true
    }
}

//shot {
//    applicationId = "com.dhontiveros.presentation"
//}

dependencies {
    modules(listOf(
//        Dependencies.Modules.commonsCore,
        Dependencies.Modules.commonsUi,
        Dependencies.Modules.commonsRobot,
        Dependencies.Modules.domain,
    ))

    // 🔽 Añade también en tests si quieres usar Hilt con @Inject/@HiltAndroidTest
//    testImplementation(project(Dependencies.Modules.commonsCore))
//    androidTestImplementation(project(Dependencies.Modules.commonsCore))

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

//    androidTestImplementation(composeBom)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)

    // UI Automator (used to create TestRule
//    androidTestImplementation(libs.androidx.uiautomator)

    // UI testing:
    // Compose
//    androidTestImplementation(libs.compose.ui.test.junit)
//    debugImplementation(libs.compose.ui.test.manifest)

    // Shot
//    androidTestImplementation(libs.shot)

    // Hilt:
//    androidTestImplementation(libs.hilt.android.testing)
//    kspTest(libs.hilt.compiler)
//    kspAndroidTest(libs.hilt.compiler)

//    // Necesario para instrumented tests con Compose + Hilt
//    androidTestImplementation("androidx.test:runner:1.5.2")
}