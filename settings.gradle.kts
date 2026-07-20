pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        // build-conventions (the shared version catalog) resolves from jitpack, pinned by commit.
        maven { url = uri("https://jitpack.io") }
    }
    // Import the org-wide shared catalog as `libs`.
    versionCatalogs {
        create("libs") {
            from("com.github.capullo-tech:build-conventions:a8439c66c46c7228e2be5fdc92e1a10e2fc693c0")
        }
    }
}

rootProject.name = "capullo-audio-contracts"
