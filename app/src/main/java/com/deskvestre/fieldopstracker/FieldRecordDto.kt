package com.deskvestre.fieldopstracker

data class FieldRecordDto(
    val id: String,
    val lat: Double,
    val lng: Double,
    val note: String?,
    val createdAt: String
)
