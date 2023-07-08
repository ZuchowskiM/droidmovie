package com.mzuch.droidmovie.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzuch.droidmovie.data.model.RepositoryResult
import com.mzuch.droidmovie.data.movies.repository.MovieDataSource
import com.mzuch.droidmovie.movies.intent.MoviesIntent
import com.mzuch.droidmovie.movies.viewstate.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepo: MovieDataSource
) : ViewModel() {

    val moviesIntent = Channel<MoviesIntent>(Channel.UNLIMITED)
    val moviesState = MutableStateFlow<MoviesState>(MoviesState.Idle)

    init {
        handleIntent()
        listenToMovies()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            moviesIntent.consumeAsFlow().collect {
                when (it) {
                    is MoviesIntent.FetchData -> fetchData()
                    is MoviesIntent.MarkAsFavorite -> markAsFavorite(it.movieUid)
                    is MoviesIntent.UnMarkAsFavorite -> unMarkAsFavorite(it.movieUid)
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

    private fun listenToMovies() {
        viewModelScope.launch {
            movieRepo.getLiveMoviesData().collect {
                moviesState.value = MoviesState.Success(it)
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            when (movieRepo.loadMoviesData()) {
                is RepositoryResult.Error -> {
                    moviesState.value = MoviesState.Error
                    moviesState.value = MoviesState.Idle
                }
                is RepositoryResult.Success -> {}
            }
        }
    }
}