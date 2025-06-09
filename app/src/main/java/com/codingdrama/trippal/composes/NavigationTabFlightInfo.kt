package com.codingdrama.trippal.composes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codingdrama.trippal.R
import com.codingdrama.trippal.viewnodel.FlightMainViewModel

enum class Destination(val label: String, val route: String, val icon: ImageVector? = null, val contentDescription: String = "") {
    DEPARTURES("DEPARTURES", "departures"), ARRIVALS("ARRIVALS", "arrivals")
}

@Composable
fun NavigationTabFlightInfo(modifier: Modifier = Modifier, flightViewModel: FlightMainViewModel) {
    val navController = rememberNavController()
    val startDestination = Destination.DEPARTURES
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(modifier = modifier) { contentPadding ->
        Column {
            PrimaryTabRow(
                selectedTabIndex = selectedDestination
            ) {
                Destination.entries.forEachIndexed { index, destination ->
                    Tab(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        text = {
                            Text(
                                text = destination.label,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
            AppNavHost(navController, startDestination, modifier = modifier.weight(1.0f), flightViewModel)
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    flightViewModel: FlightMainViewModel
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.DEPARTURES -> DeparturesScreen(modifier, flightViewModel)
                    Destination.ARRIVALS -> ArrivesScreen(modifier, flightViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeparturesScreen(modifier: Modifier = Modifier, flightViewModel: FlightMainViewModel) {
    val state = rememberPullToRefreshState()
    val instantSchedulesDeparture by flightViewModel.instantSchedulesDeparture.collectAsState()
    val lineTypeCheckStatus by flightViewModel.lineTypeCheckStatus.collectAsState()

    if (instantSchedulesDeparture == null || instantSchedulesDeparture?.instantSchedules?.isEmpty() == true) {
        NoFlightInformationBox(modifier)
        return
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val displayList = instantSchedulesDeparture?.instantSchedules?.filter {
        if (lineTypeCheckStatus.first && lineTypeCheckStatus.second) {
            true // Show both domestic and international flights
        } else if (lineTypeCheckStatus.first) {
            it.airLineType == 1 // Show only domestic flights
        } else if (lineTypeCheckStatus.second) {
            it.airLineType == 0 // Show only international flights
        } else {
            false // Show no flights
        }
    } ?: emptyList()

    PullToRefreshBox(
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            flightViewModel.getFlightInfoDepartures()
            isRefreshing = false
        },
        modifier = modifier
    ) {
        LazyColumn {
            items(displayList.size) { index ->
                val flightInfo = displayList[index]
                CardFlightInfo(instantSchedule = flightInfo, cardType = CardType.DEPARTURE)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrivesScreen(modifier: Modifier = Modifier, flightViewModel: FlightMainViewModel) {
    val state = rememberPullToRefreshState()
    val instantSchedulesArrive by flightViewModel.instantSchedulesArrive.collectAsState()
    val lineTypeCheckStatus by flightViewModel.lineTypeCheckStatus.collectAsState()

    if (instantSchedulesArrive == null || instantSchedulesArrive?.instantSchedules?.isEmpty() == true) {
        NoFlightInformationBox(modifier)
        return
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val displayList = instantSchedulesArrive?.instantSchedules?.filter {
        if (lineTypeCheckStatus.first && lineTypeCheckStatus.second) {
            true // Show both domestic and international flights
        } else if (lineTypeCheckStatus.first) {
            it.airLineType == 1 // Show only domestic flights
        } else if (lineTypeCheckStatus.second) {
            it.airLineType == 0 // Show only international flights
        } else {
            false // Show no flights
        }
    } ?: emptyList()

    PullToRefreshBox(
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            flightViewModel.getFlightInfoArrives()
            isRefreshing = false
        },
        modifier = modifier
    ) {
        LazyColumn {
            items(displayList.size) { index ->
                val flightInfo = displayList[index]
                CardFlightInfo(instantSchedule = flightInfo, cardType = CardType.ARRIVAL)
            }
        }
    }
}

@Composable
fun NoFlightInformationBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = LocalContext.current.getString(R.string.title_no_flight_information_available),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}