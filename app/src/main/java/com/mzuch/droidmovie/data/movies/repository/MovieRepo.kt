package com.mzuch.droidmovie.data.movies.repository

import com.mzuch.droidmovie.data.model.RepositoryResult
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MoviesData
import com.mzuch.droidmovie.data.movies.repository.local.MovieLocalSource
import com.mzuch.droidmovie.data.movies.repository.remote.MovieRemoteSource
import com.mzuch.droidmovie.network.GenericResponse
import com.mzuch.droidmovie.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class MovieRepo(private val remote: MovieRemoteSource, private val local: MovieLocalSource) :
    MovieDataSource {

    override suspend fun loadMoviesData(): RepositoryResult<MoviesData> {
        when (val result = remote.getMoviesData()) {
            is NetworkResponse.ApiError -> return RepositoryResult.Error()
            is NetworkResponse.NetworkError -> return RepositoryResult.Error()
            is NetworkResponse.Success -> {
                val movieResults = result.body.results.map {
                    MovieEntity(
                        UUID.randomUUID().toString(),
                        it.title.orEmpty(),
                        it.posterPath,
                        it.releaseDate.orEmpty(),
                        it.voteAverage,
                        it.overview.orEmpty(),
                    )
                }
                local.deleteAll()
                local.insertAll(movieResults)
                return RepositoryResult.Success(result.body)
            }
            is NetworkResponse.UnknownError -> return RepositoryResult.Error()
        }
    }

    override suspend fun getLiveMoviesData(): Flow<List<MovieEntity>> {
        return local.getAll()
    }
}