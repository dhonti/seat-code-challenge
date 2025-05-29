object Dependencies {

    object Modules {
        const val presentation = ":presentation"
        const val domain = ":domain"
        const val roverRobot = ":rover-robot"
    }

}

object DependenciesGroups {
    val modules = listOf(
        Dependencies.Modules.presentation,
        Dependencies.Modules.domain,
        Dependencies.Modules.roverRobot,
    )
}