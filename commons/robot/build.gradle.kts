plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = Dependencies.Modules.commonsRobot.toNamespace()
    compileSdk = Config.compileSdk

    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }
    kotlinOptions {
        jvmTarget = Config.javaVersion.majorVersion
    }
}

dependencies {
    // Dagger:
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Moshi:
    api(libs.bundles.moshi)
    ksp(libs.moshi.kotlin.codegen)
}