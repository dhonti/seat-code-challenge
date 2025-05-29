plugins {
    id("java-library") // o 'com.android.library' si necesitas Android
    kotlin("jvm")
}

java {
    sourceCompatibility = Config.javaVersion
    targetCompatibility = Config.javaVersion
}