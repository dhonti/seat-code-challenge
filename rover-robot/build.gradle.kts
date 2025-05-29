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
    modules(listOf(Dependencies.Modules.commonsRobot))

    // Dagger
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // TESTING
    // ----------------------------------
    testImplementation(libs.bundles.unit.tests)

}
