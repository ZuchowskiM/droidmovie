package com.mzuch.droidmovie.moviedetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzuch.droidmovie.data.movies.repository.MovieDataSource
import com.mzuch.droidmovie.moviedetails.intent.MovieDetailsIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepo: MovieDataSource
) : ViewModel() {

    val moviesDetailsIntent = Channel<MovieDetailsIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            moviesDetailsIntent.consumeAsFlow().collect {
                when (it) {
                    is MovieDetailsIntent.MarkAsFavorite -> markAsFavorite(it.movieUid)
                    is MovieDetailsIntent.UnMarkAsFavorite -> unMarkAsFavorite(it.movieUid)
                }
            }
        }
    }

    private fun markAsFavorite(movieUid: Int) {
        viewModelScope.launch {
            movieRepo.markFavorite(movieUid)
        }
    }

    private fun unMarkAsFavorite(movieUid: Int) {
        viewModelScope.launch {
            movieRepo.unMarkFavorite(movieUid)
        }
    }
}