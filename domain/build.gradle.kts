plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = Config.javaVersion
    targetCompatibility = Config.javaVersion
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(Config.jvmTarget)
    }
}

dependencies {
    implementation(project(":rover-robot"))
}
