package com.deskvestre.fieldopstracker.data.remote.network

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall())
    } catch (e: HttpException) {
        NetworkResult.ServerError(e.code(), e.message())
    } catch (e: JsonSyntaxException) {
        NetworkResult.ParsingError(e.message ?: "Error al interpretar la respuesta")
    } catch (e: IOException) {
        NetworkResult.NetworkError(e)
    }
}