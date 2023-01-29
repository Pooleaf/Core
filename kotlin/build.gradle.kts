plugins {
    kotlin("jvm") version "1.7.20"
}

repositories {
}

dependencies {
    // Core
    compileOnly(project(":java"))

    // Kotlin
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}