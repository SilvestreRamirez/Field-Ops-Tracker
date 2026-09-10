package com.deskvestre.fieldopstracker.ui.viemodel

sealed class SyncError {
    data class Server(val code: Int, val message: String) : SyncError()
    data class Network(val message: String) : SyncError()
    data class Parsing(val message: String) : SyncError()
}