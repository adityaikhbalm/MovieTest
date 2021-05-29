package plugin

import com.android.build.gradle.BaseExtension
import constant.AndroidConfig
import constant.BuildType
import constant.BuildTypeRelease
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureDefaultPlugins() {
    plugins.apply("kotlin-android")
}

internal fun Project.configureAndroidApp() =  this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(AndroidConfig.Version.compileSdkVersion)
    defaultConfig {
        minSdkVersion(AndroidConfig.Version.minSdkVersion)
        targetSdkVersion(AndroidConfig.Version.targetSdkVersion)
        testInstrumentationRunner = AndroidConfig.Android.testInstrumentationRunner
    }
    buildTypes {
        named(BuildType.RELEASE) {
            if (project.name == "app") {
                isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
                multiDexEnabled = BuildTypeRelease.isMultiDex
                proguardFiles = mutableListOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    file("proguard-rules.pro")
                )
            }

            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    project.tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }

    buildFeatures.viewBinding = true
}