package com.example.moviedb.data.remote

import com.example.moviedb.utils.safeLog
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.reactivex.*
import io.reactivex.functions.Function
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type


class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    private val instance = RxJava2CallAdapterFactory.createAsync()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? =
        RxCallAdapterWrapper(
            retrofit,
            instance.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>
        )
}

class RxCallAdapterWrapper<R>(
    private val retrofit: Retrofit,
    private val wrapped: CallAdapter<R, Any>
) : CallAdapter<R, Any> {

    override fun responseType(): Type = wrapped.responseType()

    override fun adapt(call: Call<R>): Any {
        val result = wrapped.adapt(call)
        return when (result) {
            is Single<*> -> {
                result.onErrorResumeNext(Function<Throwable, SingleSource<Nothing>> { throwable ->
                    Single.error(convertToBaseException(throwable))
                })
            }

            is Observable<*> -> {
                result.onErrorResumeNext(Function<Throwable, ObservableSource<Nothing>> { throwable ->
                    Observable.error(convertToBaseException(throwable))
                })
            }

            is Completable -> {
                result.onErrorResumeNext { throwable ->
                    Completable.error(convertToBaseException(throwable))
                }
            }

            is Flowable<*> -> {
                result.onErrorResumeNext(Function<Throwable, Flowable<Nothing>> { throwable ->
                    Flowable.error(convertToBaseException(throwable))
                })
            }

            is Maybe<*> -> {
                result.onErrorResumeNext(Function<Throwable, Maybe<Nothing>> { throwable ->
                    Maybe.error(convertToBaseException(throwable))
                })
            }

            else -> result
        }
    }

    private fun convertToBaseException(throwable: Throwable): BaseException =
        when (throwable) {
            is BaseException -> throwable

            is IOException -> BaseException.toNetworkError(throwable)

            is HttpException -> {
                val response = throwable.response()
                val httpCode = throwable.code().toString()

                if (response.errorBody() == null) {
                    BaseException.toHttpError(
                        httpCode = httpCode,
                        response = response
                    )
                }

                val serverErrorResponseBody = try {
                    response.errorBody()?.string() ?: ""
                } catch (e: Exception) {
                    e.safeLog()
                    ""
                }

                val serverErrorResponse =
                    try {
                        Gson().fromJson(serverErrorResponseBody, ServerErrorResponse::class.java)
                    } catch (e: Exception) {
                        e.safeLog()
                        ServerErrorResponse()
                    }

                if (serverErrorResponse != null) {
                    BaseException.toServerError(
                        serverErrorResponse = serverErrorResponse,
                        httpCode = httpCode
                    )
                } else {
                    BaseException.toHttpError(
                        response = response,
                        httpCode = httpCode
                    )
                }
            }

            else -> BaseException.toUnexpectedError(throwable)
        }
}

class BaseException(
    val errorType: ErrorType,
    val serverErrorResponse: ServerErrorResponse? = null,
    val response: Response<*>? = null,
    val httpCode: String = "",
    cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {

    override val message: String?
        get() = when (errorType) {
            ErrorType.HTTP -> response?.message()

            ErrorType.NETWORK -> cause?.message

            ErrorType.SERVER -> serverErrorResponse?.message // TODO update real response

            ErrorType.UNEXPECTED -> cause?.message
        }

    companion object {
        fun toHttpError(response: Response<*>, httpCode: String) =
            BaseException(
                errorType = ErrorType.HTTP,
                response = response,
                httpCode = httpCode
            )

        fun toNetworkError(cause: Throwable) =
            BaseException(
                errorType = ErrorType.NETWORK,
                cause = cause
            )

        fun toServerError(serverErrorResponse: ServerErrorResponse, httpCode: String) =
            BaseException(
                errorType = ErrorType.SERVER,
                serverErrorResponse = serverErrorResponse,
                httpCode = httpCode
            )

        fun toUnexpectedError(cause: Throwable) =
            BaseException(
                errorType = ErrorType.UNEXPECTED,
                cause = cause
            )
    }
}

/**
 * Identifies the error type which triggered a [BaseException]
 */
enum class ErrorType {
    /**
     * An [IOException] occurred while communicating to the server.
     */
    NETWORK,

    /**
     * A non-2xx HTTP status code was received from the server.
     */
    HTTP,

    /**
     * A error server with code & message
     */
    SERVER,

    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
}

// TODO update server error response
data class ServerErrorResponse(
    @SerializedName("message") val message: String? = null
)
