package com.deskvestre.fieldopstracker.domain.model

sealed class SyncResult {
    data class Success(val hasChanges: Boolean) : SyncResult()
    data class ServerError(val code: Int, val message: String) : SyncResult()
    data class NetworkError(val message: String) : SyncResult()
    data class ParsingError(val message: String) : SyncResult()
}