package com.adityaikhbalm.moviedb.features.popular.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityaikhbalm.libraries.ui.recyclerview.ItemOffsetDecoration
import com.adityaikhbalm.moviedb.features.popular.adapter.PopularLoadingStateAdapter
import com.adityaikhbalm.moviedb.features.popular.adapter.PopularPagingAdapter

fun RecyclerView.setPopularAdapter(popularPagingAdapter: PopularPagingAdapter) {
    val gridLayout = GridLayoutManager(context, 2)
    gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (popularPagingAdapter.getItemViewType(position)) {
                PopularPagingAdapter.TYPE_ERROR -> 2
                PopularPagingAdapter.TYPE_DEFAULT -> 1
                else -> 1
            }
        }
    }

    run {
        setHasFixedSize(false)
        layoutManager = gridLayout
        addItemDecoration(ItemOffsetDecoration(20))
        adapter = popularPagingAdapter.withLoadStateHeaderAndFooter(
            header = PopularLoadingStateAdapter(popularPagingAdapter),
            footer = PopularLoadingStateAdapter(popularPagingAdapter)
        )
    }
}
