package com.mzuch.droidmovie.movies.view

import androidx.recyclerview.widget.RecyclerView
import com.mzuch.droidmovie.data.movies.model.Results
import com.mzuch.droidmovie.databinding.ItemMovieBinding

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Results) {
        binding.titleTv.text = movie.title.orEmpty()
    }
}