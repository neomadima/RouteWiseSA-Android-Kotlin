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
        // Mapbox Maven Repository
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create("basic", org.gradle.authentication.http.BasicAuthentication::class.java)
            }
            credentials {
                username = "mapbox"
                password = providers.gradleProperty("MAPBOX_DOWNLOADS_TOKEN").getOrElse("sk.eyJ1Ijoic3QxMDQ0NDE2MyIsImEiOiJjbXRpamRpajkwMTc5MnpyMjFxZnBobTZ3In0.jXJUYZNtRzt1ec1BkeOaag")
            }
        }
    }
}

rootProject.name = "RouteWiseSA"
include(":app")
