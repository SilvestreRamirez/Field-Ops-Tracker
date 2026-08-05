package com.deskvestre.fieldopstracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: FieldRecordRepository
) : ViewModel() {

    val pendingFieldRecords: StateFlow<List<FieldRecord>> = repository.observePending().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun add(record: FieldRecord) {
        viewModelScope.launch {
            repository.add(record)
        }
    }
}