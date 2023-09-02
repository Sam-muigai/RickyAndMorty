package com.samkt.rickyandmorty.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class Result<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Result<T>(data)
    class Error(message: String?) : Result<Nothing>(message = message)
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T,
): Result<T> {
    return withContext(Dispatchers.IO){
        try {
            val response = apiCall.invoke()
            Result.Success(response)
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    Result.Error("Server error occurred!!")
                }

                is IOException -> {
                    Result.Error("No internet connection")
                }

                else -> {
                    Result.Error(throwable.message)
                }
            }
        }
    }
}
