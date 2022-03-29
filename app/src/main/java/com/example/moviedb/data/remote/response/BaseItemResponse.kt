package com.example.moviedb.data.remote.response

import com.squareup.moshi.Json

class BaseItemResponse<Item>(
    @Json(name = "item") val item: Item? = null
) : BaseResponse()