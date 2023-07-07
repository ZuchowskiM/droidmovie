package com.mzuch.droidmovie.movies.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.databinding.ItemMovieBinding
import com.mzuch.droidmovie.moviedetails.view.MovieDetailsArgsData

class MovieAdapter(
    private val onClick: (MovieDetailsArgsData) -> Unit,
    private val markFavorite: (Int) -> Unit,
    private val unMarkFavorite: (Int) -> Unit
) :
    ListAdapter<MovieEntity, MovieViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, onClick, markFavorite, unMarkFavorite)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
                oldItem.uid == newItem.uid

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}