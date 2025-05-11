pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SportsBetting"

include(":app")
include(":core")
include(":data")
include(":domain")
include(":feature:betting")
include(":feature:cart")
include(":network")
include(":analytics")
