package com.adityaikhbalm.moviedb

import android.content.Context
import androidx.multidex.MultiDex
import com.adityaikhbalm.core.cache.di.cacheModule
import com.adityaikhbalm.core.data.di.dataModule
import com.adityaikhbalm.core.domain.di.domainModule
import com.adityaikhbalm.core.remote.di.remoteModule
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieApp : SplitCompatApplication() {
    private val moduleList by lazy {
        listOf(
            domainModule,
            dataModule,
            cacheModule,
            remoteModule
        )
    }

    override fun onCreate() {
        super.onCreate()
        injectKoin()
    }

    private fun injectKoin() {
        startKoin {
            androidContext(this@MovieApp)
            androidLogger()
            modules(moduleList)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        SplitCompat.install(this)
    }
}
