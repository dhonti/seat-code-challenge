import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.io.FilenameFilter

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.detekt)

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false

    alias(libs.plugins.ben.manes.versions)
    alias(libs.plugins.version.catalog.update)
}

detekt {
    // Defining custom "detekt.yml" file rules:
    config = files("$projectDir/detekt.yml")
    // Directories list that have to analyze (except hidden directories with prefix ".")
    source = files(rootProject.rootDir.listFiles { _, name -> !name.startsWith(".") })
    // Start with default configuration and apply after custom rules from file "detekt.yml"
    buildUponDefaultConfig = true
}

allprojects {
    apply<com.github.benmanes.gradle.versions.VersionsPlugin>()

    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    tasks.withType<DependencyUpdatesTask> {
        rejectVersionIf {
            isNonStable(candidate.version)
        }
    }
}