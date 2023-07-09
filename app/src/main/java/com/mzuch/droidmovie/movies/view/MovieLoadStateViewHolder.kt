package com.mzuch.droidmovie.movies.view

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.mzuch.droidmovie.databinding.ItemMovieLoadStateBinding

class MovieLoadStateViewHolder(
    private val binding: ItemMovieLoadStateBinding,
    private val retryClickListener: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(loadState: LoadState) {
        binding.run {
            pbLoading.isVisible = loadState is LoadState.Loading
            tvError.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            tvError.text = (loadState as? LoadState.Error)?.error?.message
            mbRetry.isVisible = loadState is LoadState.Error
            mbRetry.setOnClickListener {
                retryClickListener()
            }
        }
    }
}