package com.mzuch.droidmovie.movies.view

import androidx.recyclerview.widget.RecyclerView
import com.mzuch.droidmovie.data.movies.model.Results
import com.mzuch.droidmovie.databinding.ItemMovieBinding
import com.mzuch.droidmovie.moviedetails.view.MovieDetailsArgsData

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onClick: (MovieDetailsArgsData) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Results) {
        binding.titleTv.text = movie.title.orEmpty()
        binding.root.setOnClickListener {
            val args = MovieDetailsArgsData(
                movie.posterPath ?: "",
                movie.title ?: "",
                movie.releaseDate ?: "",
                movie.voteAverage.toString(),
                movie.overview ?: ""
            )
            onClick(args)
        }
    }
}