package com.mzuch.droidmovie.data.movies.model

import com.google.gson.annotations.SerializedName

class Results(
    @SerializedName("id")
    val id: Int
) {

    @SerializedName("adult")
    var adult: Boolean? = null

    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    @SerializedName("genre_ids")
    var genreIds: ArrayList<Int> = arrayListOf()

    @SerializedName("original_language")
    var originalLanguage: String? = null

    @SerializedName("original_title")
    var originalTitle: String? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("popularity")
    var popularity: Double? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("video")
    var video: Boolean? = null

    @SerializedName("vote_average")
    var voteAverage: Double? = null

    @SerializedName("vote_count")
    var voteCount: Int? = null

    override fun equals(other: Any?): Boolean {
        if (other !is Results) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (adult?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + genreIds.hashCode()
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + (originalTitle?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (popularity?.hashCode() ?: 0)
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (video?.hashCode() ?: 0)
        result = 31 * result + (voteAverage?.hashCode() ?: 0)
        result = 31 * result + (voteCount ?: 0)
        return result
    }
}