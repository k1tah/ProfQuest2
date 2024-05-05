package com.example.data.api

suspend fun <R : Any?> safeApiCall(block: suspend () -> R): Either<R> = try {
    Either.Success(block())
} catch (exception: Exception) {
    Either.Failure<Nothing>(exception)
}

sealed interface Either<out T> {
    class Success<T>(val value: T) : Either<T>
    class Failure<U>(val error: Exception) : Either<Nothing>
}

inline fun <R, T> Either<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (error: Exception) -> R
): R = when (this) {
    is Either.Success -> onSuccess(value)
    is Either.Failure<*> -> onFailure(error)
}

val <T> Either<T>.isSuccess: Boolean
    get() = this is Either.Success

val <T> Either<T>.isFailure: Boolean
    get() = this is Either.Failure<*>

fun <T> Either<T>.valueOrNull(): T? = (this as? Either.Success)?.value