plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.dhontiveros.rover_robot"
    compileSdk = Config.compileSdk

    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }

    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}
//
//java {
//    sourceCompatibility = Config.javaVersion
//    targetCompatibility = Config.javaVersion
//}
//
//kotlin {
//    compilerOptions {
//        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(Config.jvmTarget))
//    }
//}

dependencies {
    modules(listOf(Dependencies.Modules.commonsRobot))

    // Dagger
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
}
