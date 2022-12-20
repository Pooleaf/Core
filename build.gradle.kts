import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.7.20"

    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/snapshots/") // BungeeCord
    maven("https://repo.s8u.kr/repository/maven-minecraft/") // Bukkit
    maven("https://repo.s8u.kr/repository/maven-pooleaf/") // Core
    maven("https://repo.dmulloy2.net/repository/public/") // XSeries
}

dependencies {
    // Platform
    compileOnly("io.papermc:paper:1.8.8")
    compileOnly("net.md-5:bungeecord-api:1.18-R0.1-SNAPSHOT")

    // Bukkit Library
    compileOnly("com.comphenix.protocol:ProtocolLib:4.7.0")
    implementation("com.github.cryptomorin:XSeries:9.1.0")

    // SQL
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.0.3")

    // Redis
    implementation("io.lettuce:lettuce-core:6.0.0.RELEASE")

    // Annotation Library
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")


    // Test
    testImplementation(kotlin("test"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }

    withType<ShadowJar> {
        archiveClassifier.set("")

        relocate("io.netty", "net.pooleaf.core.lib.io.lettuce.netty")
    }

    register<Copy>("copyToServerWindows") {
        from(shadowJar)
        into("D:\\서버\\1.8.9 테스트 서버\\update")
    }
}