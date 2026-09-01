package com.deskvestre.fieldopstracker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.deskvestre.fieldopstracker.domain.model.FieldRecord
import com.deskvestre.fieldopstracker.ui.main.FieldRecordListContent
import com.deskvestre.fieldopstracker.ui.viemodel.FieldRecordUiState
import com.deskvestre.fieldopstracker.ui.viemodel.SyncState
import org.junit.Rule
import org.junit.Test

class FieldRecordListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_has_three_records_show_three_items() {
        val fakeRecord = listOf(
            FieldRecord(1, "a", null, 0.0, 0.0, "Note 1", 100L, false),
            FieldRecord(2, "b", null, 0.0, 0.0, "Note 2", 200L, false),
            FieldRecord(3, "c", null, 0.0, 0.0, "Note 3", 300L, false)
        )

        composeTestRule.setContent {
            FieldRecordListContent(
                uiState = FieldRecordUiState.Success(fakeRecord),
                syncState = SyncState(),
                onSyncClick = { },
                onAddClick = { }
            )
        }

        composeTestRule.onNodeWithText("Note 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Note 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Note 3").assertIsDisplayed()
    }
}