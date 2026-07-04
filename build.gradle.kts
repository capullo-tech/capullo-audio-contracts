plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
    `maven-publish`
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
