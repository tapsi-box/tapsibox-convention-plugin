plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.2.1"
    id("com.diffplug.spotless") version "6.23.3"
}

group = "box.tapsi.build"
version = "0.0.4"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.23.3")
    implementation("io.gitlab.arturbosch.detekt:io.gitlab.arturbosch.detekt.gradle.plugin:1.23.6")
    implementation("org.jetbrains.kotlinx.kover:org.jetbrains.kotlinx.kover.gradle.plugin:0.6.0")
}

gradlePlugin {
    website = "https://github.com/tapsi-box/tapsibox-convention-plugin"
    vcsUrl = "https://github.com/tapsi-box/tapsibox-convention-plugin.git"

    plugins {
        create("tapsiBoxConventions") {
            id = "box.tapsi.kotlin-conventions"
            displayName = "Tapsi Box Kotlin Conventions"
            description = "A binary Gradle convention plugin for Tapsi Box projects that standardizes build logic with Spotless, Detekt, and Kover."
            tags = listOf("kotlin", "convention", "spotless", "detekt", "kover")
            implementationClass = "box.tapsi.build.TapsiBoxConventionPlugin"
        }
    }
}

spotless {
    kotlin {
        target("src/**/*.kt")
        ktlint("1.1.0")
            .editorConfigOverride(
                mapOf(
                    "indent_size" to 2,
                    "ktlint_standard_filename" to "disabled",
                    "ktlint_standard_max-line-length" to "120"
                )
            )
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

tasks.register("verifyReadmePluginVersion") {
  doLast {
    val readmeFile = file("README.MD")
    if (!readmeFile.exists()) throw GradleException("README.MD not found")

    // Look for the plugin block under "Apply plugin in build.gradle.kts"
    val pluginBlockRegex = """id\("box\.tapsi\.kotlin-conventions"\)\s+version\s+"([\d.]+)"""".toRegex()
    val match = pluginBlockRegex.find(readmeFile.readText())
      ?: throw GradleException("Could not find plugin version in README.MD")

    val readmeVersion = match.groupValues[1]
    val projectVersion = project.version.toString()

    if (readmeVersion != projectVersion) {
      throw GradleException(
        "README.MD plugin version ($readmeVersion) does not match project.version ($projectVersion)"
      )
    }

    println("README.MD plugin version matches project.version: $projectVersion")
  }
}

tasks.check {
  dependsOn("verifyReadmePluginVersion")
}

data class Check(val name: String, val expectedValue: String)
