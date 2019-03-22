package com.example.moviedb.data.remote

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.remote.response.GetMovieListResponse
import com.example.moviedb.data.remote.response.GetTvListResponse
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MockApi : ApiService {

    override fun getMovieList(
        hashMap: HashMap<String, String>
    ): Single<GetMovieListResponse> =
        when (200) {
            1 -> Single.error { Throwable(cause = UnknownHostException()) }
            2 -> Single.error { Throwable(cause = SocketTimeoutException()) }
            200 -> Single.just(GetMovieListResponse())
            401 -> Single.error {
                BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(message = "Test code 401"),
                    httpCode = "401"
                )
            }
            500 -> Single.error {
                BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(message = "Test code 500"),
                    httpCode = "500"
                )
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
        when (1) {
            1 -> GlobalScope.async { throw Throwable(cause = UnknownHostException()) }
            2 -> GlobalScope.async { throw Throwable(cause = SocketTimeoutException()) }
            200 -> GlobalScope.async { GetTvListResponse() }
            /*401 -> GlobalScope.async {
                BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(message = "Test code 401"),
                    httpCode = "401"
                )
            }
            500 -> Single.error {
                BaseException.toServerError(
                    serverErrorResponse = ServerErrorResponse(message = "Test code 500"),
                    httpCode = "500"
                )
            }*/
            else -> GlobalScope.async { GetTvListResponse() }
        }

}