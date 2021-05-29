package com.adityaikhbalm.moviedb.features.favorite.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.adityaikhbalm.libraries.abstraction.extensions.BaseSearch
import com.adityaikhbalm.libraries.abstraction.extensions.detailIntent
import com.adityaikhbalm.libraries.abstraction.extensions.setMenu
import com.adityaikhbalm.libraries.abstraction.extensions.viewBinding
import com.adityaikhbalm.libraries.utility.Utility.hide
import com.adityaikhbalm.libraries.utility.Utility.show
import com.adityaikhbalm.moviedb.features.favorite.adapter.FavoritePagingAdapter
import com.adityaikhbalm.moviedb.features.favorite.databinding.FragmentFavoriteBinding
import com.adityaikhbalm.moviedb.features.favorite.di.favoriteModule
import com.adityaikhbalm.moviedb.features.favorite.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.context.loadKoinModules
import com.adityaikhbalm.libraries.abstraction.R as RAbstraction
import com.adityaikhbalm.moviedb.features.favorite.R as RFavorite

class FavoriteFragment : Fragment(RFavorite.layout.fragment_favorite), BaseSearch {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val viewModel: FavoriteViewModel by stateViewModel()
    override var menuFocus = false
    override var searchText: String? = ""
    override var searchSubmit = false

    private val favoritePagingAdapter by lazy {
        FavoritePagingAdapter(
            {
                context?.startActivity(detailIntent(it?.id))
            },
            {
                if (it != null) viewModel.deleteFavorite(it)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState == null) {
            loadKoinModules(favoriteModule)
            viewModel.getAllFavorite()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        favoritePagingAdapter.addLoadStateListener {
            if (it.source.refresh is LoadState.NotLoading &&
                it.append.endOfPaginationReached && favoritePagingAdapter.itemCount < 1
            ) {
                binding.noFavorite.show()
            } else binding.noFavorite.hide()
        }

        binding.favoriteRecyclerView.run {
            setHasFixedSize(false)
            adapter = favoritePagingAdapter
        }
    }

    private fun setViewModel() {
        lifecycleScope.launchWhenResumed {
            viewModel.favorite.observe(viewLifecycleOwner) {
                favoritePagingAdapter.submitData(lifecycle, it)
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
            { viewModel.getAllFavorite() },
            { if (searchText != null) viewModel.searchFavorite(searchText!!) }
        )
    }
}
