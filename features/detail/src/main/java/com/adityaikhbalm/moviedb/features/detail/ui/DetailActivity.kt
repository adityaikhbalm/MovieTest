package com.adityaikhbalm.moviedb.features.detail.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.adityaikhbalm.core.model.response.Movie
import com.adityaikhbalm.libraries.abstraction.extensions.startActivity
import com.adityaikhbalm.libraries.abstraction.extensions.statusLoader
import com.adityaikhbalm.libraries.abstraction.extensions.viewBinding
import com.adityaikhbalm.libraries.abstraction.interactor.ResultState
import com.adityaikhbalm.libraries.utility.Constant.movie_id
import com.adityaikhbalm.moviedb.features.detail.adapter.CastAdapter
import com.adityaikhbalm.moviedb.features.detail.adapter.SimilarAdapter
import com.adityaikhbalm.moviedb.features.detail.adapter.TrailerAdapter
import com.adityaikhbalm.moviedb.features.detail.databinding.ActivityDetailBinding
import com.adityaikhbalm.moviedb.features.detail.di.detailModule
import com.adityaikhbalm.moviedb.features.detail.viewmodel.DetailViewModel
import com.google.android.play.core.splitcompat.SplitCompat
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.context.loadKoinModules

class DetailActivity : AppCompatActivity() {

    private val movieId by lazy { intent.getIntExtra(movie_id, 0) }
    private val binding by viewBinding(ActivityDetailBinding::inflate)
    private val viewModel: DetailViewModel by stateViewModel()
    private var data: Movie? = null
    private var isFavorite = 0
    private var initialLoad = true

    private val detailAdapter = arrayOf(
        CastAdapter(), TrailerAdapter(),
        SimilarAdapter {
            val bundle = Pair(movie_id, it.id)
            startActivity<DetailActivity>(bundle)
        }
    )

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            loadKoinModules(detailModule)
            viewModel.detailMovie(movieId)
        }

        setButton()
        setAdapter()
        setViewModel()
    }

    private fun setButton() {
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.titleLayout.btnFavorite.setOnClickListener {
            data?.let {
                if (isFavorite == 0) {
                    isFavorite = 1
                    viewModel.insertFavorite(it)
                    binding.setButtonFavorite(this@DetailActivity, isFavorite)
                } else {
                    isFavorite = 0
                    viewModel.deleteFavorite(it)
                    binding.setButtonFavorite(this@DetailActivity, isFavorite)
                }
            }
        }
    }

    private fun setAdapter() {
        binding.run {
            castLayout.detailRecyclerCast.run {
                setHasFixedSize(false)
                adapter = detailAdapter[0]
            }
            trailerLayout.detailRecyclerTrailer.run {
                setHasFixedSize(false)
                adapter = detailAdapter[1]
            }
            similarLayout.detailRecyclerSimilar.run {
                setHasFixedSize(false)
                adapter = detailAdapter[2]
            }
        }
    }

    private fun setViewModel() {
        viewModel.detail.observe(this) {
            isFavorite = it.data?.favorite ?: 0

            val view = arrayOf(binding.detailNestedScrollView as View)
            val shimmer = arrayOf(binding.shimmerPoster)
            if (initialLoad) {
                it.statusLoader(binding = binding.detailLoader, view = view, shimmer = shimmer) {
                    viewModel.detailMovie(movieId)
                }
            }

            if (it is ResultState.Success && initialLoad) {
                initialLoad = false
                binding.run {
                    data = it.data
                    setHeader(this@DetailActivity, it.data)
                    setButtonFavorite(this@DetailActivity, isFavorite)
                    setCategory(detailAdapter, it.data)
                }
            }
        }
    }
}
