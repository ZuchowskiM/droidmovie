package com.mzuch.droidmovie.movies.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.mzuch.droidmovie.databinding.ItemMovieLoadStateBinding

class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        val binding =
            ItemMovieLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieLoadStateViewHolder(binding, retry)
    }
}