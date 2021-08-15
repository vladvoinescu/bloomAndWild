package ro.smartsociety.data.model

sealed class Response<out T> {

    data class Success<out T>(val value: T) : Response<T>()

    data class Failure(val error: Throwable? = null) : Response<Nothing>()
}
