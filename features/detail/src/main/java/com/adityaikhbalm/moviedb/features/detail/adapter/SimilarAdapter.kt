package com.adityaikhbalm.moviedb.features.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adityaikhbalm.core.model.response.Similar
import com.adityaikhbalm.libraries.abstraction.diffutil.SimilarItemDiffUtil
import com.adityaikhbalm.libraries.abstraction.extensions.imageLoader
import com.adityaikhbalm.libraries.utility.Utility.toastShow
import com.adityaikhbalm.moviedb.features.detail.databinding.ItemSimilarBinding

class SimilarAdapter(
    private val onClick: (Similar) -> Unit
) : ListAdapter<Similar, SimilarAdapter.Holder>(SimilarItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemSimilarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(
        private val binding: ItemSimilarBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(similar: Similar) {
            binding.run {
                similarBackground.setOnClickListener {
                    onClick(similar)
                }
                similarBackground.setOnLongClickListener {
                    binding.root.context.toastShow(similar.title)
                    return@setOnLongClickListener false
                }

                similarText.text = similar.title
                similarImage.imageLoader(shimmer = shimmerSimilar, type = 1, url = similar.posterPath)
            }
        }
    }
}
