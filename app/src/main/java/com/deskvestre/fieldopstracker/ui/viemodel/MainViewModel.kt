package com.deskvestre.fieldopstracker.ui.viemodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deskvestre.fieldopstracker.domain.model.FieldRecord
import com.deskvestre.fieldopstracker.domain.usecase.AddFieldRecordUseCase
import com.deskvestre.fieldopstracker.domain.usecase.GetFieldRecordUseCase
import com.deskvestre.fieldopstracker.domain.usecase.SyncFieldRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val addFieldRecordUseCase: AddFieldRecordUseCase,
    private val syncFieldRecordUseCase: SyncFieldRecordUseCase,
    private val getFieldRecordsUseCase: GetFieldRecordUseCase,
) : ViewModel() {

    val uiState: StateFlow<FieldRecordUiState> =
        getFieldRecordsUseCase()
            .map<List<FieldRecord>, FieldRecordUiState> { records ->
                FieldRecordUiState.Success(records)
            }
            .catch { e ->
                emit(FieldRecordUiState.Error(e.message ?: "Unknown Error"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FieldRecordUiState.Loading
            )

    private val _syncState = MutableStateFlow(SyncState())
    val syncState: StateFlow<SyncState> = _syncState.asStateFlow()

    fun add(record: FieldRecord) {
        viewModelScope.launch {
            addFieldRecordUseCase(record)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sync() {
        viewModelScope.launch {
            _syncState.value = SyncState(isSyncing = true)
            try {
                syncFieldRecordUseCase()
                _syncState.value = SyncState(isSyncing = false)
            } catch (e: Exception) {
                _syncState.value =
                    SyncState(isSyncing = false, syncError = e.message ?: "Unknown Error")
            }
        }
    }
}