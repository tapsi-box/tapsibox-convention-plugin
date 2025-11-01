# Tapsi Box Kotlin Conventions Plugin

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.22-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-8.0+-green.svg)](https://gradle.org)
[![Detekt](https://img.shields.io/badge/Detekt-1.23.6-blue.svg)](https://detekt.github.io/detekt/)
[![Spotless](https://img.shields.io/badge/Spotless-6.23.3-orange.svg)](https://github.com/diffplug/spotless)
[![Kover](https://img.shields.io/badge/Kover-0.9.3-purple.svg)](https://kotlin.github.io/kotlinx-kover/)

A binary Gradle convention plugin to standardize build logic for Tapsi Box Kotlin projects using Spotless, Detekt, and Kover.

## Overview

The Tapsi Box Kotlin Conventions Plugin provides a standardized set of Gradle plugins, tasks, and configurations for Kotlin-based projects within the Tapsi Box ecosystem. It ensures consistent code formatting, static analysis, test coverage verification, and testing conventions across all generated projects.

By centralizing these build conventions, teams can maintain high code quality and uniform project structure without repeating boilerplate Gradle configurations.

## Features

### 🔧 Shared Plugins
- **Spotless** – automatic Kotlin code formatting
- **Detekt** – static code analysis
- **Kover** – code coverage verification

### ⚡ Shared Tasks & Configuration
- **Spotless checks** for Kotlin files with custom rules
- **Detekt configuration** using baseline and custom rules
- **Kover verification** with exclusion support

### 🛠 Extension Support
- `tapsiBoxConvention { }` extension to customize Kover excludes or other future defaults

## Installation

### 1) Add pluginManagement (optional)

If you want to rely on the Plugin Portal (default), nothing is required in settings.gradle.kts — Gradle uses the portal by default. For clarity you can add:
```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
````

### 2) Apply plugin in build.gradle.kts:

```kotlin
plugins {
    id("box.tapsi.kotlin-conventions") version "0.0.5-RC1"
}
```
Replace the version with the published release version you want to use.


## Quick Start

### 1. Apply Plugin

Add `id("box.tapsi.kotlin-conventions")` to your project plugins block.

### 2. Configure Optional Extension

```kotlin
tapsiBoxConvention {
    koverExcludes.add("box.tapsi.generated.*")
    koverMinCoveragePercentage.set(5)
}
```
### 3. Run Shared Tasks

```
./gradlew spotlessCheck
./gradlew detekt
./gradlew check
```

All tasks and checks will be applied according to the standardized Tapsi Box conventions.

## Configuration

### Kover

Customize which packages are excluded from code coverage:

```kotlin
tapsiBoxConvention {
    koverExcludes.add("my.custom.package.*")
}
```

You can also set the minimum acceptable code coverage percentage using `koverMinCoveragePercentage`:

```kotlin
tapsiBoxConvention {
    koverMinCoveragePercentage.set(80)
}
```
This ensures that the project fails the build if code coverage falls below 80%.

### Detekt

The plugin automatically uses the project's `config/detekt/detekt.yml` file if it exists, applying the rules defined there to perform static analysis. You can override the configuration file or specific rules in your project as needed.

## Version Compatibility

| Plugin Version | Kotlin | Gradle | Java |
|----------------|--------| ------ |------|
| 0.0.5-RC1      | 1.9.23 | 8+     | 17+  |

## Dependencies

* **Spotless 6.23.3** – Kotlin formatting
* **Detekt 1.23.6** – Static analysis
* **Kover 0.6.0** – Coverage verification

## License

MIT License – see the [LICENSE](LICENSE) file for details.

## Support

* **Issues:** [GitHub Issues](https://github.com/tapsi-box/tapsibox-convention-plugin/issues)
* **Discussions:** [GitHub Discussions](https://github.com/tapsi-box/tapsibox-convention-plugin/discussions)

## Authors

* **Shahryar Safizadeh** – [@shahryarSafizadeh](https://github.com/shahryarSafizadeh/)

## Acknowledgments

* Gradle Team for providing the plugin API
* Spotless, Detekt, and Kover for enabling high code quality
* All contributors and users of this library

---

**Made with ❤️ by the Tapsi team**
