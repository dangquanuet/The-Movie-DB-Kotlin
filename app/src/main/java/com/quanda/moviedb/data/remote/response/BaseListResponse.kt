package com.quanda.moviedb.data.remote.response

import com.google.gson.annotations.SerializedName

open class BaseListResponse<Item> : BaseResponse() {

    @SerializedName("page")
    var page: Int = 0

    @SerializedName("total_results")
    var totalResults: Int = 0

    @SerializedName("total_pages")
    var totalPages: Int = 0

    @SerializedName("results")
    var results: ArrayList<Item> = ArrayList()
}