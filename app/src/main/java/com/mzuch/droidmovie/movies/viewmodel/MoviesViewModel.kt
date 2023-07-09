package com.mzuch.droidmovie.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzuch.droidmovie.data.movies.repository.MovieDataSource
import com.mzuch.droidmovie.movies.intent.MoviesIntent
import com.mzuch.droidmovie.movies.viewstate.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepo: MovieDataSource
) : ViewModel() {

    val moviesIntent = Channel<MoviesIntent>(Channel.UNLIMITED)
    val moviesEvents = MutableSharedFlow<MoviesState>()
    val moviesPagedFlow = movieRepo.getMoviesFromPagingMediator()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            moviesIntent.consumeAsFlow().collect {
                when (it) {
                    is MoviesIntent.MarkAsFavorite -> markAsFavorite(it.movieUid)
                    is MoviesIntent.UnMarkAsFavorite -> unMarkAsFavorite(it.movieUid)
                    is MoviesIntent.RefreshError -> emitError()
                }
            }
        }
    }

    private fun emitError() {
        viewModelScope.launch {
            moviesEvents.emit(MoviesState.Error(ERROR_MSG))
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

    private companion object {
        const val ERROR_MSG = "Ups...something went wrong, check your internet connection"
    }
}