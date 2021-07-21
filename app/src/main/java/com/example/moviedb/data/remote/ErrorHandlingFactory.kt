package com.example.moviedb.data.remote

import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

/*
import com.example.moviedb.utils.safeLog
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.*
import io.reactivex.functions.Function
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type


*/
/**
 * RxErrorHandlingFactory for rxjava
 *//*
class RxErrorHandlingFactory : CallAdapter.Factory() {

    private val instance = RxJava2CallAdapterFactory.createAsync()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? =
        RxCallAdapterWrapper(
            instance.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>
        )
}

class RxCallAdapterWrapper<R>(
    private val wrapped: CallAdapter<R, Any>
) : CallAdapter<R, Any> {

    override fun responseType(): Type = wrapped.responseType()

    override fun adapt(call: Call<R>): Any {
        val result = wrapped.adapt(call)
        return when (result) {
            is Single<*> -> {
                result.onErrorResumeNext(Function<Throwable, SingleSource<Nothing>> { throwable ->
                    Single.error(throwable.toBaseException())
                })
            }

            is Observable<*> -> {
                result.onErrorResumeNext(Function<Throwable, ObservableSource<Nothing>> { throwable ->
                    Observable.error(throwable.toBaseException())
                })
            }

            is Completable -> {
                result.onErrorResumeNext { throwable ->
                    Completable.error(throwable.toBaseException())
                }
            }

            is Flowable<*> -> {
                result.onErrorResumeNext(Function<Throwable, Flowable<Nothing>> { throwable ->
                    Flowable.error(throwable.toBaseException())
                })
            }

            is Maybe<*> -> {
                result.onErrorResumeNext(Function<Throwable, Maybe<Nothing>> { throwable ->
                    Maybe.error(throwable.toBaseException())
                })
            }

            else -> result
        }
    }
}*/


fun Throwable.toBaseException(): BaseException {
    return when (val throwable = this) {
        is BaseException -> throwable

        is IOException -> BaseException.toNetworkError(throwable)

        is HttpException -> {
            val response = throwable.response()
            val httpCode = throwable.code()

            if (response?.errorBody() == null) {
                return BaseException.toHttpError(
                    httpCode = httpCode,
                    response = response
                )
            }

            val serverErrorResponseBody = try {
                response.errorBody()?.string() ?: ""
            } catch (e: Exception) {
                Timber.e(e)
                ""
            }

            val serverErrorResponse =
                try {
                    Moshi.Builder().build().adapter(ServerErrorResponse::class.java)
                        .fromJson(serverErrorResponseBody)
                } catch (e: Exception) {
                    Timber.e(e)
                    ServerErrorResponse()
                }

            if (serverErrorResponse != null) {
                BaseException.toServerError(
                    serverErrorResponse = serverErrorResponse,
                    response = response,
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
    val httpCode: Int = 0,
    cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {

    override val message: String?
        get() = when (errorType) {
            ErrorType.HTTP -> response?.message()

            ErrorType.NETWORK -> cause?.message

            ErrorType.SERVER -> serverErrorResponse?.errors?.getOrNull(0) // TODO update error message from server

            ErrorType.UNEXPECTED -> cause?.message
        }

    companion object {
        fun toHttpError(response: Response<*>?, httpCode: Int) =
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

        fun toServerError(
            serverErrorResponse: ServerErrorResponse,
            response: Response<*>?,
            httpCode: Int
        ) = BaseException(
            errorType = ErrorType.SERVER,
            serverErrorResponse = serverErrorResponse,
            response = response,
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
    val message: String? = null,
    val errors: List<String>? = null
)
