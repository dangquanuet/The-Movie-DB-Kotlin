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
import java.lang.RuntimeException
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
                    return BaseException.toHttpError(response)
                }
                try {
                    val errorResponse = response.errorBody()?.string() ?: ""
                    val baseErrorResponse =
                        Gson().fromJson(errorResponse, BaseErrorResponse::class.java)
                    if (baseErrorResponse != null) {
                        //Get error data from Server
                        baseErrorResponse.code = throwable.code().toString()
                        return BaseException.toServerError(baseErrorResponse)
                    } else {
                        //Get error data cause http connection
                        return BaseException.toHttpError(response)
                    }
                } catch (e: Exception) {
                    BaseException.toUnexpectedError(throwable)
                }
            }

            else -> BaseException.toUnexpectedError(throwable)
        }
    }
}

class BaseException(
    val errorType: ErrorType,
    val errorResponse: BaseErrorResponse? = null,
    val response: Response<*>? = null,
    cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {

    val serverErrorCode: String
        get() = errorResponse?.code ?: ""

    override val message: String?
        get() = when (errorType) {
            ErrorType.SERVER -> errorResponse?.errors?.getOrNull(0)?.name?.getOrNull(0)

            ErrorType.NETWORK -> cause?.message

            ErrorType.HTTP -> response?.message()

            ErrorType.UNEXPECTED -> cause?.message
        }

    companion object {
        fun toHttpError(response: Response<*>): BaseException =
            BaseException(errorType = ErrorType.HTTP, response = response)

        fun toNetworkError(cause: Throwable): BaseException =
            BaseException(errorType = ErrorType.NETWORK, cause = cause)

        fun toServerError(errorResponse: BaseErrorResponse): BaseException =
            BaseException(errorType = ErrorType.SERVER, errorResponse = errorResponse)

        fun toUnexpectedError(cause: Throwable): BaseException =
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
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("errors") val errors: List<Error>? = null
)

class Error(
    @SerializedName("itemname") val name: List<String>? = null
)