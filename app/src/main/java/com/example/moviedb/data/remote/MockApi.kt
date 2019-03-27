package com.example.moviedb.data.remote

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import com.example.moviedb.data.scheduler.SchedulerProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MockApi(
    private val schedulerProvider: SchedulerProvider
) : ApiService {

    override fun getMovieList(
        hashMap: HashMap<String, String>
    ): Deferred<GetMovieListResponse> =
        when (HttpURLConnection.HTTP_UNAUTHORIZED) {
            1 -> {
                schedulerProvider.ioScope.async {
                    throw BaseException.toNetworkError(
                        cause = UnknownHostException()
                    )
                }
            }

            2 -> {
                schedulerProvider.ioScope.async {
                    throw BaseException.toNetworkError(
                        cause = SocketTimeoutException()
                    )
                }
            }

            HttpURLConnection.HTTP_OK -> {
                schedulerProvider.ioScope.async { GetMovieListResponse() }
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                schedulerProvider.ioScope.async {
                    throw BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 401"
                        ),
                        httpCode = HttpURLConnection.HTTP_UNAUTHORIZED
                    )
                }
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                schedulerProvider.ioScope.async {
                    throw BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 500"
                        ),
                        httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR
                    )
                }
            }

            else -> schedulerProvider.ioScope.async { GetMovieListResponse() }
        }

    override fun getMovieDetail(
        hashMap: HashMap<String, String>
    ): Deferred<Movie> =
        schedulerProvider.ioScope.async { Movie("1") }

    override fun getTvList(
        hashMap: HashMap<String, String>
    ): Deferred<GetTvListResponse> =
        when (HttpURLConnection.HTTP_INTERNAL_ERROR) {
            1 -> {
                schedulerProvider.ioScope.async {
                    throw BaseException.toNetworkError(
                        cause = UnknownHostException()
                    )
                }
            }

            2 -> {
                schedulerProvider.ioScope.async {
                    throw BaseException.toNetworkError(
                        cause = SocketTimeoutException()
                    )
                }
            }

            HttpURLConnection.HTTP_OK -> {
                schedulerProvider.ioScope.async { GetTvListResponse() }
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                schedulerProvider.ioScope.async {
                    throw BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 401"
                        ),
                        httpCode = HttpURLConnection.HTTP_UNAUTHORIZED
                    )
                }
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                schedulerProvider.ioScope.async {
                    throw BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 500"
                        ),
                        httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR
                    )
                }
            }

            else -> schedulerProvider.ioScope.async { GetTvListResponse() }
        }

}