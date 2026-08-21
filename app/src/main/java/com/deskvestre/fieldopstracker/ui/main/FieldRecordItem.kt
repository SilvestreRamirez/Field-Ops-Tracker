package com.deskvestre.fieldopstracker.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deskvestre.fieldopstracker.domain.model.FieldRecord

@Composable
fun FieldRecordItem(record: FieldRecord, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = record.notes, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Lat: ${record.gpsLat}, Lng: ${record.gpsLng}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = if (record.isSynced) "✅ Synced" else "⏳ Pending",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}


@Preview
@Composable
fun FieldRecordItemPreview() {
    FieldRecordItem(
        record = FieldRecord(
            id = 0,
            localId = "0",
            serverId = null,
            gpsLat = 0.0,
            gpsLng = 0.0,
            notes = "Some notes",
            timestamp = 0,
            isSynced = false,
        )
    )
}