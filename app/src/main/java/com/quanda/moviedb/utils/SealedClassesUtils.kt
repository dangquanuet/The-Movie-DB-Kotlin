package com.quanda.moviedb.utils

sealed class NetworkResult
data class Success(val result: String) : NetworkResult()
data class Failure(val error: String) : NetworkResult()

fun doWork() {
    val data1: NetworkResult = Success("")
    when (data1) {
        is Success -> println(data1.result)
        is Failure -> println(data1.error)
    }

    val data2: NetworkResult = Failure("")
    when (data2) {
        is Success -> println(data2.result)
        is Failure -> println(data2.error)
    }
}

// viewHolders in recyclerViewAdapter
// listeners in recyclerViewAdapter