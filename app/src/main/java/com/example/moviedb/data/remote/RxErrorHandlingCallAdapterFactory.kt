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

    override fun responseType(): Type {
        return wrapped.responseType()
    }

    override fun adapt(call: Call<R>): Any {
        val result = wrapped.adapt(call)
        return when (result) {
            is Single<*> -> {
                result.onErrorResumeNext(Function<Throwable, SingleSource<Nothing>> { throwable ->
                    //                    Single.error<Nothing>(convertToRetrofitException(throwable))
                    Single.error<Nothing>(convertToBaseException(throwable))
                })
            }
            is Observable<*> -> {
                result.onErrorResumeNext(Function<Throwable, ObservableSource<Nothing>> { throwable ->
                    //                    Observable.error<Nothing>(convertToRetrofitException(throwable))
                    Observable.error<Nothing>(convertToBaseException(throwable))
                })
            }
            is Completable -> {
                result.onErrorResumeNext { throwable ->
                    //                    Completable.error(convertToRetrofitException(throwable))
                    Completable.error(convertToBaseException(throwable))
                }
            }
            else -> result
        }
    }

    private fun convertToRetrofitException(throwable: Throwable): RetrofitException {
        return when (throwable) {
            // We had non-200 http error
            is HttpException -> {
                val response = throwable.response()
                RetrofitException.httpError(
                    response.raw().request().url().toString(),
                    response,
                    retrofit
                )
            }
            // A network error happened
            is IOException -> {
                RetrofitException.networkError(throwable)
            }
            // We don't know what happened. We need to simply convert to an unknown error
            else -> {
                RetrofitException.unexpectedError(throwable)
            }
        }
    }

    private fun convertToBaseException(throwable: Throwable): BaseException {
        try {
            if (throwable is BaseException) {
                return throwable
            }

            if (throwable is HttpException) {
                val response = throwable.response()
                if (response.errorBody() == null) {
                    return BaseException.toHttpError(response)
                }
                try {
                    val errorResponse = response.errorBody()?.string() ?: ""
                    val baseErrorResponse =
                        Gson().fromJson(errorResponse, BaseErrorResponse::class.java)
                    return if (baseErrorResponse != null) {
                        //Get error data from Server
                        baseErrorResponse.code = throwable.code()
                        BaseException.toServerError(baseErrorResponse)
                    } else {
                        //Get error data cause http connection
                        BaseException.toHttpError(response)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return BaseException.toUnexpectedError(throwable)
        } catch (e: Exception) {
            return BaseException.toUnexpectedError(throwable)
        }
    }
}

class RetrofitException constructor(
    message: String? = null,
    /** The request URL which produced the error.  */
    val url: String? = null,
    /** Response object containing status code, headers, body, etc.  */
    val response: Response<*>? = null,
    /** The event kind which triggered this error.  */
    val kind: Kind, exception: Throwable? = null,
    /** The Retrofit this request was executed on  */
    val retrofit: Retrofit? = null
) : RuntimeException(message, exception) {

    /** Identifies the event kind which triggered a [RetrofitException].  */
    enum class Kind {
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK,
        /** A non-200 HTTP status code was received from the server.  */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    /**
     * HTTP response body converted to specified `type`. `null` if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified `type`.
     */
    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        if (response == null || response.errorBody() == null || retrofit == null) {
            return null
        }
        return retrofit.responseBodyConverter<T>(type, arrayOfNulls(0))
            .convert(response.errorBody()!!)
    }

    companion object {
        fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
            val message = "${response.code()} ${response.message()}"
            return RetrofitException(message, url, response, Kind.HTTP, null, retrofit)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message, null, null, Kind.NETWORK, exception, null)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(
                exception.message,
                null,
                null,
                Kind.UNEXPECTED,
                exception,
                null
            )
        }
    }
}

class BaseException : RuntimeException {

    var errorType: Int = NETWORK
    var errorResponse: BaseErrorResponse? = null
    var response: Response<*>? = null

    val serverErrorCode: Int
        get() = if (errorResponse != null
            && errorResponse!!.code != null
        ) errorResponse!!.code!! else -1

    constructor(type: Int, cause: Throwable) : super(cause.message, cause) {
        this.errorType = type
    }

    constructor(type: Int, errorResponse: BaseErrorResponse?) {
        this.errorType = type
        this.errorResponse = errorResponse
    }

    constructor(type: Int, response: Response<*>?) {
        this.errorType = type
        this.response = response
    }

    override val message: String?
        get() = when (errorType) {
            SERVER -> if (errorResponse != null
                && errorResponse!!.errors != null
                && errorResponse!!.errors!!.isNotEmpty()
                && errorResponse!!.errors!!.get(0).name != null
                && errorResponse!!.errors!!.get(0).name!!.isNotEmpty()
            ) {
                errorResponse!!.errors!!.get(0).name!!.get(0)
            } else null

            NETWORK -> getNetworkErrorMessage(cause)

            HTTP -> if (response != null
                && response!!.message().isNotEmpty()
            ) {
                response!!.message()
            } else null

            UNEXPECTED -> cause?.message

            else -> {
                "Unexpected error has occurred"
            }
        }

    private fun getNetworkErrorMessage(throwable: Throwable?): String {
        return throwable?.message.toString()
    }

    companion object {

        /**
         * An [IOException] occurred while communicating to the server.
         */
        const val NETWORK = 0
        /**
         * A non-2xx HTTP status code was received from the server.
         */
        const val HTTP = 1
        /**
         * A error server with code & message
         */
        const val SERVER = 2
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        const val UNEXPECTED = 3

        fun toHttpError(response: Response<*>): BaseException {
            return BaseException(HTTP, response)
        }

        fun toNetworkError(cause: Throwable): BaseException {
            return BaseException(NETWORK, cause)
        }

        fun toServerError(errorResponse: BaseErrorResponse): BaseException {
            return BaseException(SERVER, errorResponse)
        }

        fun toUnexpectedError(cause: Throwable): BaseException {
            return BaseException(UNEXPECTED, cause)
        }
    }
}

class BaseErrorResponse(
    @field: SerializedName("code") var code: Int? = null,
    @field: SerializedName("message") var message: String? = null,
    @field: SerializedName("errors") var errors: List<Error>? = null
)

class Error(
    @field: SerializedName("itemname") val name: List<String>? = null
)