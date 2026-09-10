package com.deskvestre.fieldopstracker.data.remote

import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall())
    } catch (e: retrofit2.HttpException) {
        NetworkResult.ServerError(e.code(), e.message())
    } catch (e: com.google.gson.JsonSyntaxException) {
        NetworkResult.ParsingError(e.message ?: "Error al interpretar la respuesta")
    } catch (e: IOException) {
        NetworkResult.NetworkError(e)
    }
}