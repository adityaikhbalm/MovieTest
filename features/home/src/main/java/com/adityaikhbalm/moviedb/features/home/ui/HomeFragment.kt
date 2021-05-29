package com.adityaikhbalm.moviedb.features.home.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.adityaikhbalm.libraries.abstraction.extensions.viewBinding
import com.adityaikhbalm.moviedb.features.home.adapter.HomeAdapter
import com.adityaikhbalm.moviedb.features.home.databinding.FragmentHomeBinding
import com.adityaikhbalm.moviedb.features.home.di.homeModule
import com.adityaikhbalm.moviedb.features.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.context.loadKoinModules
import com.adityaikhbalm.libraries.abstraction.R as RAbstraction
import com.adityaikhbalm.moviedb.features.home.R as RHome

class HomeFragment : Fragment(RHome.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by stateViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState == null) {
            loadKoinModules(homeModule)
            viewModel.initialLoad()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
    }

    private fun setAdapter() {
        binding.homeRecyclerView.run {
            setHasFixedSize(false)
            adapter = HomeAdapter(viewModel)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(RAbstraction.id.search_menu).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }
}
