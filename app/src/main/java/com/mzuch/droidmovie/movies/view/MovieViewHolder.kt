package com.mzuch.droidmovie.movies.view

import androidx.recyclerview.widget.RecyclerView
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.databinding.ItemMovieBinding
import com.mzuch.droidmovie.moviedetails.view.MovieDetailsArgsData

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onClick: (MovieDetailsArgsData) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieEntity) {
        binding.titleTv.text = movie.title
        binding.root.setOnClickListener {
            val args = MovieDetailsArgsData(
                movie.posterPath ?: "",
                movie.title,
                movie.releaseDate,
                movie.score.toString(),
                movie.overview
            )
            onClick(args)
        }
    }
}