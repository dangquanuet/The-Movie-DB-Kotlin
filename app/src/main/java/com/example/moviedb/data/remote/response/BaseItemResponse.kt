package com.example.moviedb.data.remote.response

class BaseItemResponse<Item>(
    val item: Item? = null
) : BaseResponse()