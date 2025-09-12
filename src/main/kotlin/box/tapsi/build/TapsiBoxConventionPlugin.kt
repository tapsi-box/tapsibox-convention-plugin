package box.tapsi.build

import com.diffplug.gradle.spotless.SpotlessExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import kotlinx.kover.api.CounterType
import kotlinx.kover.api.KoverProjectConfig
import kotlinx.kover.api.VerificationValueType
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType

/**
 * Provides standardized build conventions for Tapsi Box projects, integrating Spotless, Detekt, and Kover.
 *
 * @author Shahryar Safizadeh
 */
@Suppress("unused")
class TapsiBoxConventionPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    applyPlugins(project)

    val extension = project.extensions.create<TapsiBoxConventionExtension>("tapsiBoxConvention")

    configureSpotless(project)
    configureDetekt(project)
    configureKover(project, extension)
  }

  private fun applyPlugins(project: Project) {
    with(project.pluginManager) {
      apply("com.diffplug.spotless")
      apply("io.gitlab.arturbosch.detekt")
      apply("org.jetbrains.kotlinx.kover")
    }
  }

  private fun configureSpotless(project: Project) {
    project.extensions.findByType<SpotlessExtension>()?.let {
      project.extensions.configure<SpotlessExtension>("spotless") {
        kotlin {
          target("src/**/*.kt")
          ktlint(KTLINT_VERSION)
            .editorConfigOverride(
              mapOf(
                "indent_size" to INDENT_SIZE,
                "ktlint_standard_filename" to "disabled",
                "ktlint_standard_max-line-length" to MAX_LINE_LENGTH.toString(),
              ),
            )
          trimTrailingWhitespace()
          indentWithSpaces()
          endWithNewline()
        }
      }
    }
  }

  private fun configureDetekt(project: Project) {
    project.extensions.findByType<DetektExtension>()?.let {
      project.extensions.configure<DetektExtension>("detekt") {
        buildUponDefaultConfig = true
        allRules = false
        config.setFrom("${project.projectDir}/config/detekt/detekt.yml")
        baseline = project.file("${project.projectDir}/config/detekt/baseline.xml")
      }
    }
  }

  private fun configureKover(project: Project, extension: TapsiBoxConventionExtension) {
    project.extensions.findByType<KoverProjectConfig>()?.let {
      project.extensions.configure<KoverProjectConfig>("kover") {
        verify {
          onCheck.set(true)
          rule {
            isEnabled = true
            name = "Verifying code coverage"
            bound {
              minValue = 71
              counter = CounterType.INSTRUCTION
              valueType = VerificationValueType.COVERED_PERCENTAGE
            }
          }
        }
        filters {
          classes {
            excludes += extension.koverExcludes.get()
          }
        }
      }
    }
  }

  companion object {
    private const val KTLINT_VERSION = "1.1.0"
    private const val INDENT_SIZE = 2
    private const val MAX_LINE_LENGTH = 120
    private const val MIN_COVERAGE_PERCENTAGE = 71
  }
}
