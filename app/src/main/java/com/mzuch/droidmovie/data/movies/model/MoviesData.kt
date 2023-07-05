package com.mzuch.droidmovie.data.movies.model

import com.google.gson.annotations.SerializedName

class MoviesData {

    @SerializedName("dates")
    var dates: Dates? = Dates()

    @SerializedName("page")
    var page: Int? = null

    @SerializedName("results")
    var results: ArrayList<Results> = arrayListOf()

    @SerializedName("total_pages")
    var totalPages: Int? = null

    @SerializedName("total_results")
    var totalResults: Int? = null
}