package com.mzuch.droidmovie.movies.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mzuch.droidmovie.data.movies.model.MovieDto
import com.mzuch.droidmovie.databinding.ItemMovieBinding
import com.mzuch.droidmovie.moviedetails.view.MovieDetailsArgsData

class MoviePagingDataAdapter(
    private val onClick: (MovieDetailsArgsData) -> Unit,
    private val markFavorite: (Int) -> Unit,
    private val unMarkFavorite: (Int) -> Unit
) : PagingDataAdapter<MovieDto, MovieViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onClick, markFavorite, unMarkFavorite)
    }

    private companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MovieDto>() {
            override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}