package com.deskvestre.fieldopstracker.data.repository

import com.deskvestre.fieldopstracker.data.local.dao.FieldRecordDao
import com.deskvestre.fieldopstracker.data.local.entity.FieldRecordEntity
import com.deskvestre.fieldopstracker.data.remote.api.FieldOpsApi
import com.deskvestre.fieldopstracker.data.remote.dto.FieldRecordDto
import com.deskvestre.fieldopstracker.domain.model.FieldRecord
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FieldRecordRepositoryImplTest {

    val dao = mockk<FieldRecordDao>()
    val api = mockk<FieldOpsApi>()
    val repository = FieldRecordRepositoryImpl(dao, api)

    @Test
    fun `Sync use case when is coming a new record from server`() = runTest {
        every { dao.getAllPending() } returns flowOf(emptyList())
        coEvery { dao.getByServerId(any()) } returns null
        coEvery {
            api.getRemoteRecords()
        } returns listOf(
            FieldRecordDto(
                id = "1",
                lat = 0.0,
                lng = 0.0,
                note = "",
                createdAt = System.currentTimeMillis().toString()
            )
        )
        coEvery { dao.insert(any()) } returns 1L
        repository.sync()

        coVerify { dao.insert(match { it.serverId == "1" && it.isSynced }) }
    }

    @Test
    fun `Sync use case when record is sync and need to be updated`() = runTest {
        every { dao.getAllPending() } returns flowOf(emptyList())
        coEvery {
            api.getRemoteRecords()
        } returns listOf(
            FieldRecordDto(
                id = "1",
                lat = 0.0,
                lng = 0.0,
                note = "Note updated from server",
                createdAt = System.currentTimeMillis().toString()
            )
        )
        coEvery { dao.getByServerId(any()) } returns FieldRecordEntity(
            id = 1L,
            localId = "1",
            serverId = "1",
            gpsLat = 0.0,
            gpsLng = 0.0,
            notes = "Old note in local",
            timestamp = System.currentTimeMillis(),
            isSynced = true
        )
        coEvery { dao.insert(any()) } returns 1L
        repository.sync()

        coVerify { dao.insert(match { it.notes == "Note updated from server" }) }
    }

    @Test
    fun `Add map domain record to entity and insert it`() = runTest {
        coEvery { dao.insert(any()) } returns 1L
        repository.add(
            FieldRecord(
                id = 0,
                localId = "abc",
                serverId = null,
                gpsLat = 1.0,
                gpsLng = 2.0,
                notes = "test",
                timestamp = 100L,
                isSynced = false
            )
        )

        coVerify { dao.insert(match { it.localId == "abc" && it.notes == "test" }) }
    }
}