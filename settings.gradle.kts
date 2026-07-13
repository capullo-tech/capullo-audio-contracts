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
            from("com.github.capullo-tech:build-conventions:b07e979")
        }
    }
}

rootProject.name = "capullo-audio-contracts"
