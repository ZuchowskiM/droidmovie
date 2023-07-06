package com.mzuch.droidmovie.data.model

sealed class RepositoryResult<out T : Any> {
    data class Success<T : Any>(val data: T) : RepositoryResult<T>()
    data class Error(val msg: String = "Unknown error") : RepositoryResult<Nothing>()
}
