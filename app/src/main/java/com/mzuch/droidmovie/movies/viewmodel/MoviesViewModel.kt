package com.mzuch.droidmovie.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                }
            }
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
            movieRepo.loadMoviesData()
        }
    }
}