package com.deskvestre.fieldopstracker.ui.viemodel

import com.deskvestre.fieldopstracker.domain.model.FieldRecord

sealed interface FieldRecordUiState {
    data object Loading : FieldRecordUiState
    data class Success(val records: List<FieldRecord>) : FieldRecordUiState
    data class Error(val message: String?) : FieldRecordUiState
}