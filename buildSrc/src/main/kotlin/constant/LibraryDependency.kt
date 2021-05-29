package constant

interface Libraries {
    val components: List<String>
}

object LibraryDependency {
    object ArchitectureComponent : Libraries {
        object Version {
            const val lifeCycle = "2.3.1"
        }

        private const val lifeCycle =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifeCycle}"
        private const val liveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifeCycle}"
        private const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifeCycle}"
        private const val common = "androidx.lifecycle:lifecycle-common-java8:${Version.lifeCycle}"

        override val components: List<String>
            get() = listOf(lifeCycle, liveData, viewModel, common)
    }

    object AppComponent : Libraries {
        object Version {
            const val appCompat = "1.3.0-rc01"
            const val activity = "1.3.0-alpha07"
            const val coreKtx = "1.5.0-rc02"
            const val fragment = "1.3.2"
        }

        const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
        private const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
        private const val activity = "androidx.activity:activity-ktx:${Version.activity}"
        private const val fragment = "androidx.fragment:fragment-ktx:${Version.fragment}"

        override val components: List<String>
            get() = listOf(appCompat, activity, fragment)
    }

    object Navigation : Libraries {
        object Version {
            const val navigation = "2.3.5"
        }

        private const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
        private const val navigationUi =
            "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
        private const val navigationDynamic =
            "androidx.navigation:navigation-dynamic-features-fragment:${Version.navigation}"

        override val components: List<String>
            get() = listOf(navigationFragment, navigationUi, navigationDynamic)
    }

    object Coroutines : Libraries {
        object Version {
            const val coroutines = "1.5.0"
        }

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
        private const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"

        override val components: List<String> = listOf(core, android)
    }

    object DI {
        object Version {
            const val koin = "3.0.2"
        }

        const val core = "io.insert-koin:koin-core:${Version.koin}"
        const val koin = "io.insert-koin:koin-android:${Version.koin}"
    }

    object Network : Libraries {
        object Version {
            const val okHttp = "4.9.1"
            const val retrofit = "2.9.0"
            const val moshi = "1.12.0"
        }

        object AnnotationProcessor {
            const val moshi = "com.squareup.moshi:moshi-kotlin-codegen:${Version.moshi}"
        }

        private const val okHttp = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
        private const val loggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"
        private const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
        const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"
        const val moshi = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"

        override val components: List<String> = listOf(
            okHttp, loggingInterceptor, retrofit, retrofitMoshi, moshi
        )
    }

    object View : Libraries {
        object Version {
            const val constraintLayout = "2.0.4"
            const val material = "1.3.0"
            const val recyclerView = "1.2.0"
            const val indicator = "1.4"
        }

        private const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
        const val material = "com.google.android.material:material:${Version.material}"
        private const val recyclerview =
            "androidx.recyclerview:recyclerview:${Version.recyclerView}"
        const val indicator =
            "com.github.wching:Android-Indefinite-Pager-Indicator:${Version.indicator}"

        override val components: List<String>
            get() = listOf(constraintLayout, material, recyclerview)
    }

    object Database : Libraries {
        object Version {
            const val room = "2.3.0"
        }

        private const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
        private const val roomKtx = "androidx.room:room-ktx:${Version.room}"
        const val roomCommon = "androidx.room:room-common:${Version.room}"

        object AnnotationProcessor {
            const val roomKapt = "androidx.room:room-compiler:${Version.room}"
        }

        override val components: List<String>
            get() = listOf(roomRuntime, roomKtx)
    }

    object Paging {
        object Version {
            const val paging = "3.0.0"
        }

        const val paging = "androidx.paging:paging-runtime:${Version.paging}"
    }

    object ImageLoader : Libraries {
        object Version {
            const val glide = "4.12.0"
            const val shimmer = "0.5.0"
            const val circle = "1.5"
        }

        private const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
        private const val shimmer = "com.facebook.shimmer:shimmer:${Version.shimmer}"
        private const val circle = "com.github.abdularis:circularimageview:${Version.circle}"

        override val components: List<String>
            get() = listOf(glide, shimmer, circle)
    }

    object GooglePlay {
        object Version {
            const val playCore = "1.10.0"
        }

        const val playCore = "com.google.android.play:core:${Version.playCore}"
    }

    object Multidex {
        object Version {
            const val multidex = "2.0.1"
        }

        const val multidex = "androidx.multidex:multidex:${Version.multidex}"
    }
}