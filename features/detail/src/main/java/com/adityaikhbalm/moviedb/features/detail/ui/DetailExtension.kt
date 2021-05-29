package com.adityaikhbalm.moviedb.features.detail.ui

import android.content.Context
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaikhbalm.core.model.response.Movie
import com.adityaikhbalm.libraries.abstraction.extensions.imageLoader
import com.adityaikhbalm.libraries.utility.Utility.colorStateList
import com.adityaikhbalm.libraries.utility.Utility.convertDpToPixel
import com.adityaikhbalm.libraries.utility.Utility.getMyColor
import com.adityaikhbalm.libraries.utility.Utility.getMyDrawable
import com.adityaikhbalm.libraries.utility.Utility.hide
import com.adityaikhbalm.libraries.utility.Utility.show
import com.adityaikhbalm.moviedb.features.detail.adapter.CastAdapter
import com.adityaikhbalm.moviedb.features.detail.adapter.SimilarAdapter
import com.adityaikhbalm.moviedb.features.detail.adapter.TrailerAdapter
import com.adityaikhbalm.moviedb.features.detail.databinding.ActivityDetailBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.adityaikhbalm.libraries.abstraction.R as RAbstraction
import com.adityaikhbalm.moviedb.features.detail.R as RDetail

fun ActivityDetailBinding.setHeader(context: Context, movie: Movie?) {
    detailImage.imageLoader(arrayOf(detailRoot), shimmerPoster, 0, movie?.backdropPath)
    val hour = (movie?.runtime ?: 0) / 60
    val min = (movie?.runtime ?: 0) % 60
    titleLayout.run {
        val runtime = when {
            hour == 0 -> "${min}m"
            min == 0 -> "${hour}h"
            else -> "${hour}h ${min}m"
        }

        if (hour == 0 && min == 0) detailRuntime.hide()
        else {
            detailRuntime.show()
            detailRuntime.text = runtime
        }

        detailTitle.text = movie?.title
        detailOverview.text = movie?.overview
        detailGenre.removeAllViews()
        movie?.genres?.let {
            for (i in it.indices) {
                if (i < 3) {
                    val chip = Chip(detailGenre.context)
                    chip.id = i
                    val genre = it[i].name
                    chip.run {
                        text = genre
                        chipStrokeWidth = 1.convertDpToPixel.toFloat()
                        chipStrokeColor = context.colorStateList(RAbstraction.color.low_white)
                        chipBackgroundColor = context.colorStateList(RAbstraction.color.high_dark)
                        setTextColor(context.getMyColor(RAbstraction.color.medium_white))
                        textSize = 13f
                        chipStartPadding = 0f
                        chipEndPadding = 0f

                        val layoutParams = ChipGroup.LayoutParams(
                            ChipGroup.LayoutParams.WRAP_CONTENT,
                            35.convertDpToPixel
                        )
                        this.layoutParams = layoutParams
                        detailGenre.addView(chip)
                    }
                }
            }
        }
    }
}

fun ActivityDetailBinding.setCategory(
    detailAdapter: Array<ListAdapter<out Any, out RecyclerView.ViewHolder>>,
    movie: Movie?
) {
    run {
        if (movie?.title.isNullOrEmpty()) titleLayout.root.hide()
        else titleLayout.root.show()

        if (movie?.credit?.cast.isNullOrEmpty()) castLayout.root.hide()
        else {
            castLayout.root.show()
            (detailAdapter[0] as CastAdapter).submitList(movie?.credit?.cast)
        }

        if (movie?.trailer?.results.isNullOrEmpty()) trailerLayout.root.hide()
        else {
            trailerLayout.root.show()
            (detailAdapter[1] as TrailerAdapter).submitList(movie?.trailer?.results)
        }

        if (movie?.similar?.results.isNullOrEmpty()) similarLayout.root.hide()
        else {
            similarLayout.root.show()
            (detailAdapter[2] as SimilarAdapter).submitList(movie?.similar?.results)
        }
    }
}

fun ActivityDetailBinding.setButtonFavorite(context: Context, favorite: Int) {
    run {
        if (favorite > 0) {
            titleLayout.btnFavorite.run {
                text = context.getString(RDetail.string.remove_favorite)
                icon = context.getMyDrawable(RDetail.drawable.ic_delete)
            }
        } else {
            titleLayout.btnFavorite.run {
                text = context.getString(RDetail.string.add_to_favorite)
                icon = context.getMyDrawable(RDetail.drawable.ic_play)
            }
        }
    }
}
