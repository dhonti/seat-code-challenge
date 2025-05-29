plugins {
    id("java-library") // o 'com.android.library' si necesitas Android
    kotlin("jvm")

    alias(libs.plugins.com.google.devtools.ksp)
}

java {
    sourceCompatibility = Config.javaVersion
    targetCompatibility = Config.javaVersion
}

dependencies {
    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
}