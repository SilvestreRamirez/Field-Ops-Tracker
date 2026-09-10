package com.deskvestre.fieldopstracker.data.remote

import java.io.IOException

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class ServerError(val code: Int, val message: String) : NetworkResult<Nothing>()
    data class NetworkError(val exception: IOException) : NetworkResult<Nothing>()
    data class ParsingError(val message: String) : NetworkResult<Nothing>()
}

