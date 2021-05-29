package com.adityaikhbalm.moviedb.features.popular.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.adityaikhbalm.libraries.abstraction.extensions.*
import com.adityaikhbalm.libraries.abstraction.interactor.ResultState
import com.adityaikhbalm.moviedb.features.popular.adapter.PopularPagingAdapter
import com.adityaikhbalm.moviedb.features.popular.databinding.FragmentPopularBinding
import com.adityaikhbalm.moviedb.features.popular.di.popularModule
import com.adityaikhbalm.moviedb.features.popular.viewmodel.PopularViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.context.loadKoinModules
import com.adityaikhbalm.libraries.abstraction.R as RAbstraction
import com.adityaikhbalm.moviedb.features.popular.R as RPopular

class PopularFragment : Fragment(RPopular.layout.fragment_popular), BaseSearch {

    private val binding by viewBinding(FragmentPopularBinding::bind)
    private val viewModel: PopularViewModel by stateViewModel()
    override var menuFocus = false
    override var searchText: String? = ""
    override var searchSubmit = false

    private val popularPagingAdapter by lazy {
        PopularPagingAdapter {
            context?.startActivity(detailIntent(it?.id))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState == null) {
            loadKoinModules(popularModule)
            viewModel.getPopular()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.popularRecyclerView.setPopularAdapter(popularPagingAdapter)
        setInitialLoad()
        setViewModel()
    }

    private fun setInitialLoad() {
        popularPagingAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                val err = ResultState.Error<Throwable>(Exception("Error Loading"))
                err.statusLoader(binding = binding.popularLoader) {
                    viewModel.getPopular()
                }
            }
            binding.popularLoader.progressCircular.isVisible = it.refresh is LoadState.Loading
            binding.popularLoader.btnRetry.isVisible = it.refresh is LoadState.Error
        }
    }

    private fun setViewModel() {
        lifecycleScope.launchWhenResumed {
            viewModel.popular.observe(viewLifecycleOwner) {
                popularPagingAdapter.submitData(lifecycle, it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchText", searchText)
        outState.putBoolean("menuFocus", menuFocus)
        outState.putBoolean("searchSubmit", searchSubmit)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        searchText = savedInstanceState?.getString("searchText")
        menuFocus = savedInstanceState?.getBoolean("menuFocus") ?: false
        searchSubmit = savedInstanceState?.getBoolean("searchSubmit") ?: false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchMenuItem = menu.findItem(RAbstraction.id.search_menu)
        this.setMenu(
            requireContext(),
            searchMenuItem,
            { viewModel.getPopular() },
            { if (searchText != null) viewModel.searchMovie(searchText!!) }
        )
    }
}
