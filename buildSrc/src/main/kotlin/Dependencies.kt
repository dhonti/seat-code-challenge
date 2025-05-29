import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {

    object Modules {
        const val presentation = ":presentation"
        const val domain = ":domain"
        const val roverRobot = ":rover-robot"
        const val commonsCore = ":commons:core"
        const val commonsUi = ":commons:ui"
        const val commonsRobot = ":commons:robot"
    }

    object NameSpaces {
        private val nameSpacesMap = mapOf(
            Modules.presentation to "com.dhontiveros.presentation",
            Modules.domain to "com.dhontiveros.domain",
            Modules.roverRobot to "com.dhontiveros.roverrobot",
            Modules.commonsCore to "com.dhontiveros.commons.core",
            Modules.commonsUi to "com.dhontiveros.commons.ui",
            Modules.commonsRobot to "com.dhontiveros.commons.robot",
        )

        fun getByModule(module: String): String =
            nameSpacesMap.getOrDefault(module, "")
    }
}

object DependenciesGroups {
    val modules = listOf(
        Dependencies.Modules.presentation,
        Dependencies.Modules.domain,
        Dependencies.Modules.roverRobot,
        Dependencies.Modules.commonsCore,
        Dependencies.Modules.commonsUi,
        Dependencies.Modules.commonsRobot,
    )
}


fun DependencyHandler.modules(values: List<String>) {
    values.forEach { value ->
        add("implementation", project(value))
    }
}

fun String.toNamespace(): String = Dependencies.NameSpaces.getByModule(this)