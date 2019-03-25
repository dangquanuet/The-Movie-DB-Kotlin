package com.example.moviedb.data.remote

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MockApi : ApiService {

    override fun getMovieList(
        hashMap: HashMap<String, String>
    ): Deferred<GetMovieListResponse> =
        when (HttpURLConnection.HTTP_OK) {
            1 -> {
                GlobalScope.async {
                    throw BaseException.toNetworkError(
                        cause = UnknownHostException()
                    )
                }
            }

            2 -> {
                GlobalScope.async {
                    throw BaseException.toNetworkError(
                        cause = SocketTimeoutException()
                    )
                }
            }

            HttpURLConnection.HTTP_OK -> {
                GlobalScope.async { GetMovieListResponse() }
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                GlobalScope.async {
                    throw BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 401"
                        ),
                        httpCode = HttpURLConnection.HTTP_UNAUTHORIZED
                    )
                }
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                GlobalScope.async {
                    throw BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 500"
                        ),
                        httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR
                    )
                }
            }
            else -> GlobalScope.async { GetMovieListResponse() }
        }

    override fun getMovieDetail(
        hashMap: HashMap<String, String>
    ): Deferred<Movie> =
        GlobalScope.async { Movie("1") }

    override fun getTvList(
        hashMap: HashMap<String, String>
    ): Deferred<GetTvListResponse> =
        when (HttpURLConnection.HTTP_INTERNAL_ERROR) {
            1 -> GlobalScope.async {
                throw BaseException.toNetworkError(
                    cause = UnknownHostException()
                )
            }

            2 -> GlobalScope.async {
                throw BaseException.toNetworkError(
                    cause = SocketTimeoutException()
                )
            }

            HttpURLConnection.HTTP_OK -> {
                GlobalScope.async { GetTvListResponse() }
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                GlobalScope.async {
                    throw BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 401"
                        ),
                        httpCode = HttpURLConnection.HTTP_UNAUTHORIZED
                    )
                }
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                GlobalScope.async {
                    throw BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 500"
                        ),
                        httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR
                    )
                }
            }
            else -> GlobalScope.async { GetTvListResponse() }
        }

}