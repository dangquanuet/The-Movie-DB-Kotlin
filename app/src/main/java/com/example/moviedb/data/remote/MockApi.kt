package com.example.moviedb.data.remote

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MockApi(
) : ApiService {

    override suspend fun getMovieListAsync(
        hashMap: HashMap<String, String>
    ): GetMovieListResponse =
        when (HttpURLConnection.HTTP_UNAUTHORIZED) {
            1 -> {

                throw BaseException.toNetworkError(
                    cause = UnknownHostException()
                )

            }

            2 -> {
                throw BaseException.toNetworkError(
                    cause = SocketTimeoutException()
                )
            }

            HttpURLConnection.HTTP_OK -> {
                GetMovieListResponse()
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                throw BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(
                        message = "Test code 401"
                    ),
                    httpCode = HttpURLConnection.HTTP_UNAUTHORIZED
                )
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                throw BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(
                        message = "Test code 500"
                    ),
                    httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR
                )
            }

            else -> GetMovieListResponse()
        }

    override suspend fun getMovieDetailAsync(
        hashMap: HashMap<String, String>
    ): Movie = Movie("1")

    override suspend fun getTvListAsync(
        hashMap: HashMap<String, String>
    ): GetTvListResponse =
        when (HttpURLConnection.HTTP_INTERNAL_ERROR) {
            1 -> {
                throw BaseException.toNetworkError(
                    cause = UnknownHostException()
                )
            }

            2 -> {
                throw BaseException.toNetworkError(
                    cause = SocketTimeoutException()
                )
            }

            HttpURLConnection.HTTP_OK -> {
                GetTvListResponse()
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                throw BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(
                        message = "Test code 401"
                    ),
                    httpCode = HttpURLConnection.HTTP_UNAUTHORIZED
                )
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                throw BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(
                        message = "Test code 500"
                    ),
                    httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR
                )
            }

            else -> GetTvListResponse()
        }

}