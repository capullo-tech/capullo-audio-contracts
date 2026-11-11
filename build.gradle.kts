plugins {
    alias(libs.plugins.spotless)
    alias(libs.plugins.kotlin.jvm)
    `java-library`
    `maven-publish`
}

// Org code style, same baseline as the apps (QuantumCast / Telecloud): Spotless drives ktlint from
// the shared build-conventions versions. `./gradlew spotlessApply` formats, `spotlessCheck` (CI) verifies.
spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**")
        ktlint(libs.versions.ktlint.get())
            .editorConfigOverride(
                mapOf(
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                    "ktlint_standard_annotation" to "disabled",
                    "max_line_length" to 100,
                    // Same two relaxations as QuantumCast: ktlint can't auto-expand wildcard
                    // imports or wrap long lines headlessly, so they'd block CI on existing code.
                    "ktlint_standard_no-wildcard-imports" to "disabled",
                    "ktlint_standard_max-line-length" to "disabled",
                ),
            )
    }
}

group = "tech.capullo.audio"
version = "0.1.0-SNAPSHOT"

kotlin {
    explicitApi()
    // Target JVM 17 bytecode without pinning a toolchain - compiles with the ambient JDK,
    // avoiding toolchain auto-provisioning.
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

dependencies {
    // Pure-JVM SPI: only coroutines (StateFlow). No Android / Media3 types leak in -
    // the engine converts MediaRequest -> Media3 MediaItem. See the platform architecture.
    api(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
}

tasks.test {
    useJUnit()
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])
            groupId = "tech.capullo.audio"
            artifactId = "capullo-audio-contracts"
        }
    }
}
