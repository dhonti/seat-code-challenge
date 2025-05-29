import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object DependenciesValues {
    const val implementation = "implementation"
    const val testImplementation = "testImplementation"
    const val androidTestImplementation = "androidTestImplementation"
}

fun DependencyHandler.modules(values: List<String>) {
    values.forEach { value ->
        add(DependenciesValues.implementation, project(value))
    }
}

fun DependencyHandler.implementation(values: List<String>) {
    values.forEach { value ->
        add(DependenciesValues.implementation, value)
    }
}