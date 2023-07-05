package com.mzuch.droidmovie.data.movies.model

import com.google.gson.annotations.SerializedName

class Dates {

    @SerializedName("maximum")
    var maximum: String? = null

    @SerializedName("minimum")
    var minimum: String? = null
}