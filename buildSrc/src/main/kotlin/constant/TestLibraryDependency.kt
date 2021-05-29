package constant

import constant.LibraryDependency.Coroutines

object TestLibraryDependency {
    object Version {
        const val junit = "4.13.2"
        const val archTest = "2.1.0"
        const val truth = "1.1.2"
        const val mockk = "1.11.0"
        const val mockWebServer = "4.9.1"

        const val robolectric = "4.3.1"

        const val androidxTest = "1.3.0"
        const val testExt = "1.1.2"
        const val espresso = "3.3.0"
        const val fragment = "1.3.3"
    }

    const val roomTest = "androidx.room:room-testing:${LibraryDependency.Database.Version.room}"

    private const val junit = "junit:junit:${Version.junit}"
    private const val archTest = "androidx.arch.core:core-testing:${Version.archTest}"
    private const val truth = "com.google.truth:truth:${Version.truth}"
    private const val mockk = "io.mockk:mockk:${Version.mockk}"
    private const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Version.mockWebServer}"
    private const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Coroutines.Version.coroutines}"

    const val robolectric = "org.robolectric:robolectric:${Version.robolectric}"

    const val testCore = "androidx.test:core:${Version.androidxTest}"
    const val testExt = "androidx.test.ext:junit:${Version.testExt}"
    const val testRunner = "androidx.test:runner:${Version.androidxTest}"
    const val testRules = "androidx.test:rules:${Version.androidxTest}"
    const val mockkAndroid = "io.mockk:mockk-android:${Version.mockk}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Version.fragment}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"

    val unitTest = listOf(
        junit, archTest, truth, mockk, mockWebServer, coroutinesTest
    )
}