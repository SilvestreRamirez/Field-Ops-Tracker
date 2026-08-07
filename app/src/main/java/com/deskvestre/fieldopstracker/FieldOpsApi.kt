package com.deskvestre.fieldopstracker

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FieldOpsApi {

    @GET("todos")
    suspend fun getRemoteRecords(): List<FieldRecordDto>

    @POST("records")
    suspend fun uploadRecord(@Body record: FieldRecordDto): FieldRecordDto

}