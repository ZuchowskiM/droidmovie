package com.mzuch.droidmovie.movies.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mzuch.droidmovie.data.movies.model.Results
import com.mzuch.droidmovie.databinding.ItemMovieBinding
import com.mzuch.droidmovie.utils.getDiffCallback

class MovieAdapter : ListAdapter<Results, MovieViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val diffCallback = getDiffCallback<Results> { oldItem, newItem ->
            oldItem.id == newItem.id
        }
    }
}