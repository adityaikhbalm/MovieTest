package com.adityaikhbalm.moviedb.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.adityaikhbalm.libraries.abstraction.R
import com.adityaikhbalm.libraries.abstraction.extensions.detailIntent
import com.adityaikhbalm.libraries.abstraction.extensions.statusLoader
import com.adityaikhbalm.libraries.abstraction.recyclerview.LifecycleRecyclerAdapter
import com.adityaikhbalm.moviedb.features.home.databinding.BannerHomeBinding
import com.adityaikhbalm.moviedb.features.home.databinding.CategoryHomeBinding
import com.adityaikhbalm.moviedb.features.home.viewmodel.HomeViewModel

class HomeAdapter(
    private val viewModel: HomeViewModel
) : LifecycleRecyclerAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = BannerHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            BannerHolder(binding)
        } else {
            val binding = CategoryHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            CategoryHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BannerHolder) holder.bind(position)
        if (holder is CategoryHolder) holder.bind(position)
    }

    inner class BannerHolder(
        private val binding: BannerHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            LinearSnapHelper().attachToRecyclerView(binding.bannerRecyclerView)

            val itemAdapter = HomeItemAdapter(position) {
                val context = binding.root.context
                context.startActivity(detailIntent(it?.id))
            }

            binding.bannerRecyclerView.run {
                setHasFixedSize(false)
                adapter = itemAdapter
            }

            viewModel.banner.observe(this@HomeAdapter) {
                it.statusLoader(binding = binding.bannerLoader) {
                    viewModel.bannerLoad()
                }
                val movie = it.data?.toMutableList()
                movie?.subList(movie.size - 15, movie.size)?.clear()
                binding.bannerIndicator.attachToRecyclerView(binding.bannerRecyclerView)
                itemAdapter.submitList(movie)
            }
        }
    }

    inner class CategoryHolder(
        private val binding: CategoryHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            val itemAdapter = HomeItemAdapter(position) {
                val context = binding.root.context
                context.startActivity(detailIntent(it?.id))
            }

            binding.categoryTv.text = binding.root.context.getString(
                if (position == 1) R.string.popular_movies
                else R.string.coming_soon
            )

            binding.categoryRecyclerView.run {
                setHasFixedSize(false)
                adapter = itemAdapter
            }

            if (position == 1) {
                viewModel.popular.observe(this@HomeAdapter) {
                    it.statusLoader(binding = binding.categoryLoader) {
                        viewModel.popularLoad()
                    }
                    itemAdapter.submitList(it.data)
                }
            } else {
                viewModel.coming.observe(this@HomeAdapter) {
                    it.statusLoader(binding = binding.categoryLoader) {
                        viewModel.comingLoad()
                    }
                    itemAdapter.submitList(it.data)
                }
            }
        }
    }

    override fun getItemCount() = 3

    override fun getItemViewType(position: Int) = position
}
