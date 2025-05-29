pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "seat-code-challenge"
include(":app")
include(":domain")
include(":rover-robot")
include(":presentation")
include(":commons:core")
include(":commons:ui")
include(":commons:robot")

project(":commons:core").projectDir = file("commons/core")
project(":commons:ui").projectDir = file("commons/ui")
project(":commons:robot").projectDir = file("commons/robot")