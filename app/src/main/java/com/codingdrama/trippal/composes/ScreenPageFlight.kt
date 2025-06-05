package com.codingdrama.trippal.composes

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codingdrama.trippal.R
import com.codingdrama.trippal.model.utils.TimeUtils
import com.codingdrama.trippal.viewnodel.FlightMainViewModel

// FlightScreen.kt
@Composable
fun FlightScreen(modifier: Modifier = Modifier, context: Context = LocalContext.current, flightViewModel: FlightMainViewModel) {
    val instantSchedulesDeparture by flightViewModel.instantSchedulesDeparture.collectAsState()
    val instantSchedulesArrive by flightViewModel.instantSchedulesArrive.collectAsState()
    val lastUpdateTime by flightViewModel.lastUpdateTime.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        // show a top bar with refresh time and a button to refresh the flight information
        Box(modifier = modifier.fillMaxWidth()) {
            // Top Bar with Refresh Button
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Text(
                        text = context.getString(R.string.title_last_updated) + TimeUtils.getTimeFullString(lastUpdateTime),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(20.dp, 10.dp, 20.dp, 0.dp)
                    )
                }
                item {
                    IconButton(
                        modifier = Modifier.padding(20.dp, 10.dp, 20.dp, 0.dp),
                        onClick = {
                            flightViewModel.getFlightInfoArrives()
                            flightViewModel.getFlightInfoDepartures()
                        },
                    ) {
                        Icon(
                            modifier = Modifier.height(24.dp).width(24.dp),
                            painter = painterResource(R.drawable.icon_refresh),
                            contentDescription = "Refresh Flight Information",
                        )
                    }
                }
            }
        }

        if (instantSchedulesArrive == null || instantSchedulesArrive?.instantSchedules?.isEmpty() == true
            || instantSchedulesDeparture == null || instantSchedulesDeparture?.instantSchedules?.isEmpty() == true) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = context.getString(R.string.title_no_flight_information_available),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            NavigationTabFlightInfo(modifier = modifier, flightViewModel = flightViewModel)
        }
    }
}