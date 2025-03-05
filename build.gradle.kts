plugins {
    java
    id("com.gradleup.shadow") version "9.0.0-beta10"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

tasks {
    runServer {
        minecraftVersion("1.20.4")
        downloadPlugins {
            modrinth("multiverse-core", "4.3.14")
            modrinth("luckperms", "v5.4.145-bukkit")
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("xyz.xenondevs.invui:invui:1.44")
    implementation("com.github.DrgnFireYellow:Kite:5b15c913b1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
