package box.tapsi.build

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 * Extension exposed by the plugin so consumers can customize defaults.
 *
 * @author Shahryar Safizadeh
 */
open class TapsiBoxConventionExtension @Inject constructor(objects: ObjectFactory) {
  val koverExcludes: ListProperty<String> = objects.listProperty(String::class.java).convention(
    listOf(
      "com.google.*",
      "grpc.gateway.protoc_gen_openapiv2.options.*",
    ),
  )

  val minCoveragePercentage: Property<Int> = objects.property(Int::class.java).convention(70)
}
