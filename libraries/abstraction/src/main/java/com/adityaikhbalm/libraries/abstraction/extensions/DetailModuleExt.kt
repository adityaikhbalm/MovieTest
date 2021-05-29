package com.adityaikhbalm.libraries.abstraction.extensions

import android.content.Context
import android.content.Intent
import com.adityaikhbalm.core.model.response.Movie
import com.adityaikhbalm.libraries.ui.R
import com.adityaikhbalm.libraries.utility.Constant
import com.adityaikhbalm.libraries.utility.Utility.toastShow
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

fun detailIntent(id: Int?) = Intent().apply {
    putExtra(Constant.movie_id, id)
    setClassName(
        "com.adityaikhbalm.moviedb",
        "com.adityaikhbalm.moviedb.features.detail.ui.DetailActivity"
    )
}

fun Context.installDetailModule(movie: Movie?, onClick: (Movie?) -> Unit) {
    val splitInstallManager = SplitInstallManagerFactory.create(this)
    val module = getString(R.string.detail)

    if (splitInstallManager.installedModules.contains(module)) onClick(movie)
    else {
        val request = SplitInstallRequest.newBuilder()
            .addModule(module)
            .build()

        splitInstallManager.startInstall(request)
            .addOnSuccessListener {
                onClick(movie)
            }
            .addOnFailureListener {
                toastShow("Error installing module")
            }
    }
}