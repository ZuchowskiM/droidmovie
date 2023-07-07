package com.mzuch.droidmovie.data.movies.repository

import com.mzuch.droidmovie.data.model.RepositoryResult
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieUpdateFavoriteEntity
import com.mzuch.droidmovie.data.movies.model.MoviesData
import com.mzuch.droidmovie.data.movies.repository.local.MovieLocalSource
import com.mzuch.droidmovie.data.movies.repository.remote.MovieRemoteSource
import com.mzuch.droidmovie.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

class MovieRepo(private val remote: MovieRemoteSource, private val local: MovieLocalSource) :
    MovieDataSource {

    override suspend fun loadMoviesData(): RepositoryResult<MoviesData> {
        when (val result = remote.getMoviesData()) {
            is NetworkResponse.ApiError -> return RepositoryResult.Error()
            is NetworkResponse.NetworkError -> return RepositoryResult.Error()
            is NetworkResponse.Success -> {
                val movieResults = result.body.results.map {
                    MovieEntity(
                        it.id,
                        it.title.orEmpty(),
                        it.posterPath,
                        it.releaseDate.orEmpty(),
                        it.voteAverage,
                        it.overview.orEmpty(),
                    )
                }
                val favoriteDataMovies = local.getAllFavorites().map {
                    MovieUpdateFavoriteEntity(
                        it.uid,
                        it.isFavorite
                    )
                }
                local.deleteAll()
                local.insertAll(movieResults)
                local.updateFavoritesAll(favoriteDataMovies)
                return RepositoryResult.Success(result.body)
            }
            is NetworkResponse.UnknownError -> return RepositoryResult.Error()
        }
    }

    override suspend fun getLiveMoviesData(): Flow<List<MovieEntity>> {
        return local.getAll()
    }

    override suspend fun markFavorite(movieUid: Int) {
        val movieEntity = local.getMovie(movieUid)
        val updatedMovie = movieEntity.copy(
            isFavorite = true
        )
        local.updateMovie(updatedMovie)
    }

    override suspend fun unMarkFavorite(movieUid: Int) {
        val movieEntity = local.getMovie(movieUid)
        val updatedMovie = movieEntity.copy(
            isFavorite = false
        )
        local.updateMovie(updatedMovie)
    }
}