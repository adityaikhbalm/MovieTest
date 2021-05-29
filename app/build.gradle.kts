import constant.AndroidConfig.Android
import constant.AndroidConfig.Version
import constant.LibraryDependency.DI
import constant.LibraryDependency.GooglePlay
import constant.LibraryDependency.Multidex
import constant.LibraryDependency.Navigation
import constant.LibraryDependency.View
import constant.ModulesDependency.coreCache
import constant.ModulesDependency.coreData
import constant.ModulesDependency.coreDomain
import constant.ModulesDependency.coreRemote
import constant.ModulesDependency.featureDetail
import constant.ModulesDependency.featureFavorite
import constant.ModulesDependency.featureHome
import constant.ModulesDependency.featurePopular
import constant.ModulesDependency.librariesAbstraction
import constant.ModulesDependency.librariesUi
import constant.ModulesDependency.librariesUtility

plugins {
    androidApplication
    baseGradlePlugin
}

android {
    defaultConfig {
        applicationId = Android.applicationId
        versionCode = Version.versionCode
        versionName = Version.versionName
    }

    dynamicFeatures = mutableSetOf(featureHome, featureFavorite, featurePopular, featureDetail)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(librariesAbstraction))
    implementation(project(librariesUi))
    implementation(project(librariesUtility))

    implementAll(Navigation.components)
    implementAll(View.components)

    implementation(DI.koin)
    implementation(GooglePlay.playCore)
    implementation(Multidex.multidex)

    implementation(project(coreData))
    implementation(project(coreDomain))
    implementation(project(coreRemote))
    implementation(project(coreCache))
}
