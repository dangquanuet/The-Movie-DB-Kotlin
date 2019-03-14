package com.example.moviedb.data.remote

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type


class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    companion object {
        fun create() = RxErrorHandlingCallAdapterFactory()
    }

    private val instance = RxJava2CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return RxCallAdapterWrapper(
            retrofit,
            instance.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>
        )
    }
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
                    Single.error<Nothing>(convertToBaseException(throwable))
                })
            }

            is Observable<*> -> {
                result.onErrorResumeNext(Function<Throwable, ObservableSource<Nothing>> { throwable ->
                    Observable.error<Nothing>(convertToBaseException(throwable))
                })
            }

            is Completable -> {
                result.onErrorResumeNext { throwable ->
                    Completable.error(convertToBaseException(throwable))
                }
            }

            else -> result
        }
    }

    private fun convertToBaseException(throwable: Throwable): BaseException {
        return when (throwable) {
            is BaseException -> throwable

            is IOException -> BaseException.toNetworkError(throwable)

            is HttpException -> {
                val response = throwable.response()
                if (response.errorBody() == null) {
                    BaseException.toHttpError(response)
                }

                val serverErrorResponse = try {
                    response.errorBody()?.string() ?: ""
                } catch (e: Exception) {
                    ""
                }

                val baseErrorResponse =
                    try {
                        Gson().fromJson(serverErrorResponse, BaseErrorResponse::class.java)
                    } catch (e: Exception) {
                        BaseErrorResponse()
                    }

                if (baseErrorResponse != null) {
                    baseErrorResponse.httpCode = throwable.code().toString()
                    BaseException.toServerError(baseErrorResponse)
                } else {
                    BaseException.toHttpError(response)
                }
            }

            else -> BaseException.toUnexpectedError(throwable)
        }
    }
}

class BaseException(
    val errorType: ErrorType,
    val baseErrorResponse: BaseErrorResponse? = null,
    val response: Response<*>? = null,
    cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {

    val serverErrorCode: String
        get() = baseErrorResponse?.httpCode ?: ""

    override val message: String?
        get() = when (errorType) {
            ErrorType.HTTP -> response?.message()

            ErrorType.NETWORK -> cause?.message

            ErrorType.SERVER -> baseErrorResponse?.message // TODO update real response

            ErrorType.UNEXPECTED -> cause?.message
        }

    companion object {
        fun toHttpError(response: Response<*>): BaseException =
            BaseException(errorType = ErrorType.HTTP, response = response)

        fun toNetworkError(cause: Throwable): BaseException =
            BaseException(errorType = ErrorType.NETWORK, cause = cause)

        fun toServerError(baseErrorResponse: BaseErrorResponse) =
            BaseException(errorType = ErrorType.SERVER, baseErrorResponse = baseErrorResponse)

        fun toUnexpectedError(cause: Throwable) =
            BaseException(errorType = ErrorType.UNEXPECTED, cause = cause)
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

class BaseErrorResponse(
    var httpCode: String? = null,
    @SerializedName("message") val message: String? = null
    // TODO update real response
)
