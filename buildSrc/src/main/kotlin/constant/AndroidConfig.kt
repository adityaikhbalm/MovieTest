package constant

object AndroidConfig {
    object Version {
        private const val versionMajor = 1
        private const val versionMinor = 0
        private const val versionPatch = 0

        const val versionCode = versionMajor * 100 + versionMinor * 10 + versionPatch
        const val versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        const val compileSdkVersion = 30
        const val targetSdkVersion = 30
        const val minSdkVersion = 21
    }

    object Android {
        const val applicationId = "com.adityaikhbalm.moviedb"
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

interface BuildType {
    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
    val isMultiDex: Boolean
    val isTestCoverageEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
    override val isMultiDex = false
    override val isTestCoverageEnabled = true

    const val applicationIdSuffix = ".debug"
    const val versionNameSuffix = "-DEBUG"
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = false
    override val isMultiDex = true
    override val isTestCoverageEnabled = false
}