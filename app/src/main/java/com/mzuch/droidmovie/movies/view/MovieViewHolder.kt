package com.mzuch.droidmovie.movies.view

import androidx.recyclerview.widget.RecyclerView
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.databinding.ItemMovieBinding
import com.mzuch.droidmovie.moviedetails.view.MovieDetailsArgsData

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onClick: (MovieDetailsArgsData) -> Unit,
    private val markFavorite: (Int) -> Unit,
    private val unMarkFavorite: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieEntity?) {
        binding.titleTv.text = movie?.title.orEmpty()
        binding.root.setOnClickListener {
            val args = MovieDetailsArgsData(
                movie?.id ?: 0,
                movie?.posterPath.orEmpty(),
                movie?.title.orEmpty(),
                movie?.releaseDate.orEmpty(),
                movie?.score.toString(),
                movie?.overview.orEmpty(),
                movie?.isFavorite ?: false,
            )
            onClick(args)
        }
        binding.btnFavorite.isChecked = movie?.isFavorite ?: false
        binding.btnFavorite.setOnClickListener {
            when (movie?.isFavorite) {
                true -> unMarkFavorite(movie.id)
                false -> markFavorite(movie.id)
                null -> {}
            }
        }
    }
}