plugins {
    id("com.diffplug.spotless")
}

spotless {
    kotlin {
        target(fileTree(mapOf(
            "dir" to ".",
            "include" to "**/*.kt",
            "exclude" to listOf("**/build/**", "**/buildSrc/**", "**/.*", ".idea/"))))
        ktlint("0.41.0").userData(mapOf("disabled_rules" to "no-wildcard-imports"))
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
    format("xml") {
        target("**/res/**/*.xml")
        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        target(fileTree(mapOf(
            "dir" to ".",
            "include" to listOf("**/*.gradle.kts", "*.gradle.kts"),
            "exclude" to listOf("**/build/**"))))
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}