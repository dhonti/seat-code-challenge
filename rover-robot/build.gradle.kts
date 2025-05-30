plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = Dependencies.Modules.roverRobot.toNamespace()
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
        Dependencies.Modules.commonsRobot
    ))

    // Dagger
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Unit tests:
    testImplementation(libs.bundles.unit.tests)

}
