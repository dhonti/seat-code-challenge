plugins {
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
    modules(listOf(Dependencies.Modules.roverRobot))
}
