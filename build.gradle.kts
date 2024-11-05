plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.0"
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.20.4")
        downloadPlugins {
            modrinth("multiverse-core", "4.3.12")
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
    implementation("xyz.xenondevs.invui:invui:1.40")
    implementation("com.github.DrgnFireYellow:Kite:5b15c913b1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
