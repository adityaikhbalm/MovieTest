import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("base-gradle-plugin") {
            id = "base-gradle-plugin"
            implementationClass = "plugin.BaseGradlePlugin"
        }
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = Plugin.Version.kotlin
}

object Plugin {
    object Version {
        const val androidGradle = "4.2.1"
        const val kotlin = "1.5.0"
        const val spotless = "5.12.5"
    }

    const val androidGradle = "com.android.tools.build:gradle:${Version.androidGradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:${Version.spotless}"
}

dependencies {
    implementation(Plugin.androidGradle)
    implementation(Plugin.kotlin)
    implementation(Plugin.spotless)
}

tasks.named("cleanTest") {
    group = "verification"
}