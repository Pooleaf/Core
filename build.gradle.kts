import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()

    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    maven(url = "https://repo.dmulloy2.net/repository/public/")
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))

    compileOnly("org.bukkit:bukkit:1.12.2-R0.1-SNAPSHOT")
    compileOnly("net.md-5:bungeecord-api:1.18-R0.1-SNAPSHOT")

    compileOnly("com.comphenix.protocol:ProtocolLib:4.7.0")

    implementation("com.zaxxer:HikariCP:4.0.3")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
    }

    withType<ShadowJar> {
        archiveClassifier.set("")
    }

    register<Copy>("copyToServer") {
        from(shadowJar)
        into("E:\\Desktop\\게임\\서버\\1.12.2 버킷 테스트 서버\\plugins")
    }

    register<Copy>("copyToServerKdf") {
        from(shadowJar)
        into("E:\\jhyu\\paper-1.12.2\\plugins")
    }

//    shadowJar {
//        exclude("net/pooleaf/core/test")
//        exclude("net/pooleaf/core/test/**")
//    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}