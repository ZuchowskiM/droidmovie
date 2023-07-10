package com.mzuch.droidmovie.movies

import com.mzuch.droidmovie.MainDispatcherRule
import com.mzuch.droidmovie.data.movies.repository.MovieDataSource
import com.mzuch.droidmovie.movies.intent.MoviesIntent
import com.mzuch.droidmovie.movies.viewmodel.MoviesViewModel
import com.mzuch.droidmovie.movies.viewstate.MoviesState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MoviesViesModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var mockMovieRepository: MovieDataSource

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun initMocks() {
        `when`(mockMovieRepository.getMoviesFromPagingMediator()).thenReturn(flow { })
        viewModel = MoviesViewModel(mockMovieRepository)
    }

    @Test
    fun `when RefreshError intent, return state MovieState Error`() = runTest {
        val values = mutableListOf<MoviesState>()
        val collectJob = launch {
            viewModel.moviesEvents.toList(values)
        }
        viewModel.moviesIntent.send(MoviesIntent.RefreshError)
        advanceUntilIdle()
        assertEquals(values.count(), 1)
        assertEquals(values[0], MoviesState.LoadError)
        collectJob.cancel()
    }

    @Test
    fun `when MarkAsFavorite intent, call markFavorite from MovieRepo`() = runTest {
        val movieUid = 420
        viewModel.moviesIntent.send(MoviesIntent.MarkAsFavorite(movieUid))
        advanceUntilIdle()
        verify(mockMovieRepository).markFavorite(movieUid)
    }

    @Test
    fun `when UnMarkAsFavorite intent, call unMarkFavorite from MovieRepo`() = runTest {
        val movieUid = 420
        viewModel.moviesIntent.send(MoviesIntent.UnMarkAsFavorite(movieUid))
        advanceUntilIdle()
        verify(mockMovieRepository).unMarkFavorite(movieUid)
    }
}