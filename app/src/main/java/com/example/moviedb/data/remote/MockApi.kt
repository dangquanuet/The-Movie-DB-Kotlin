package com.example.moviedb.data.remote

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MockApi : ApiService {

    override fun getMovieList(
        hashMap: HashMap<String, String>
    ): Single<GetMovieListResponse> =
        when (HttpURLConnection.HTTP_OK) {
            1 -> {
                Single.error {
                    Throwable(
                        cause = UnknownHostException()
                    )
                }
            }

            2 -> {
                Single.error {
                    Throwable(
                        cause = SocketTimeoutException()
                    )
                }
            }

            HttpURLConnection.HTTP_OK -> {
                Single.just(GetMovieListResponse())
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                Single.error {
                    BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 401"
                        ),
                        httpCode = HttpURLConnection.HTTP_UNAUTHORIZED
                    )
                }
            }

            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                Single.error {
                    BaseException.toServerError(
                        serverErrorResponse = ServerErrorResponse(
                            message = "Test code 500"
                        ),
                        httpCode = HttpURLConnection.HTTP_INTERNAL_ERROR
                    )
                }
            }
            else -> Single.just(GetMovieListResponse())
        }

    override fun getMovieDetail(
        hashMap: HashMap<String, String>
    ): Single<Movie> =
        Single.just(Movie("1"))

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