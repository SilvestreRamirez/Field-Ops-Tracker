package com.deskvestre.fieldopstracker.domain.model

data class FieldRecord(
    val id: Long,
    val localId: String,
    val serverId: String? = null,
    val gpsLat: Double,
    val gpsLng: Double,
    val notes: String,
    val timestamp: Long,
    val isSynced: Boolean = false,
)
