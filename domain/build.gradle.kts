plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = Dependencies.Modules.domain.toNamespace()
    compileSdk = Config.compileSdk

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
        Dependencies.Modules.commonsCore,
        Dependencies.Modules.roverRobot,
    ))

    // Dagger
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Testing Coroutines
    testImplementation(libs.kotlinx.coroutines.test)
}
