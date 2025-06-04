package com.codingdrama.trippal.composes

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codingdrama.trippal.R
import com.codingdrama.trippal.model.network.data.InstantSchedule

@Composable
fun CardFlightInfo (modifier: Modifier = Modifier, context: Context = LocalContext.current, instantSchedule: InstantSchedule) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .absolutePadding(20.dp, 10.dp, 20.dp, 10.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            // Time and Airport Information
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(context.getString(R.string.title_estimated_arrive_time), style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = instantSchedule.expectTime.ifEmpty { "HH:mm" },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column {
                    Text(context.getString(R.string.title_arrive_time), style = MaterialTheme.typography.labelSmall)
                    Text(
                        text = instantSchedule.realTime.ifEmpty { "HH:mm" },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // departure and arrive airport information
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(instantSchedule.upAirportCode, style = MaterialTheme.typography.titleMedium)
                }

                Text("|", style = MaterialTheme.typography.bodyMedium)

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(context.getString(R.string.title_airport_code_khh), style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Flight Number and Boarding Gate Information
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(context.getString(R.string.title_plane_number) + " : ", style = MaterialTheme.typography.labelSmall)
                Text(instantSchedule.airLineNum, style = MaterialTheme.typography.bodyMedium)
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(context.getString(R.string.title_gate) + " : ", style = MaterialTheme.typography.labelSmall)
                Text(instantSchedule.airBoardingGate, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Status Information
            val statusColor = when {
                instantSchedule.airFlyStatus.contains("Arrived", ignoreCase = true) -> Color(0xFF00C853)
                instantSchedule.airFlyStatus.contains("Departed", ignoreCase = true) -> Color(0xFF00C853)
                instantSchedule.airFlyStatus.contains("On Time", ignoreCase = true) -> Color(0xFFDDAA00)
                instantSchedule.airFlyStatus.contains("Cancelled", ignoreCase = true) -> Color(0xFFD50000)
                else -> Color.Gray
            }

            val statusText = when {
                instantSchedule.airFlyStatus.contains("Arrived", ignoreCase = true) -> context.getString(R.string.title_status_arrived)
                instantSchedule.airFlyStatus.contains("Departed", ignoreCase = true) -> context.getString(R.string.title_status_departure)
                instantSchedule.airFlyStatus.contains("On Time", ignoreCase = true) -> context.getString(R.string.title_status_on_time)
                instantSchedule.airFlyStatus.contains("Cancelled", ignoreCase = true) -> context.getString(R.string.title_status_cancelled)
                else -> ""
            }

            Text(
                text = statusText,
                color = statusColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}