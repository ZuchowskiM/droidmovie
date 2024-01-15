package com.mzuch.droidmovie.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mzuch.droidmovie.movies.intent.MoviesIntent
import com.mzuch.droidmovie.movies.usecase.MoviesUseCase
import com.mzuch.droidmovie.movies.viewstate.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    val moviesIntent = Channel<MoviesIntent>(Channel.UNLIMITED)
    val moviesEvents = MutableSharedFlow<MoviesState>()
    val moviesPagedFlow = moviesUseCase.getMoviesFlow().cachedIn(viewModelScope)

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
            moviesEvents.emit(MoviesState.LoadError)
        }
    }

    private fun markAsFavorite(movieUid: Int) {
        viewModelScope.launch {
            moviesUseCase.markAsFavorite(movieUid)
        }
    }

    private fun unMarkAsFavorite(movieUid: Int) {
        viewModelScope.launch {
            moviesUseCase.unMarkAsFavorite(movieUid)
        }
    }
}