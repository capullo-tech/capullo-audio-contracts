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
            from("com.github.capullo-tech:build-conventions:22483910a0cd6d7e583ec3d268ad1c8f872bb4ba")
        }
    }
}

rootProject.name = "capullo-audio-contracts"
