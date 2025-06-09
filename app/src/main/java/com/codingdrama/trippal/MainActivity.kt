package com.codingdrama.trippal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.codingdrama.trippal.composes.BottomNavigationBar
import com.codingdrama.trippal.composes.FlightScreen
import com.codingdrama.trippal.composes.RateScreen
import com.codingdrama.trippal.composes.Screen
import com.codingdrama.trippal.ui.theme.TripPalTheme
import com.codingdrama.trippal.viewnodel.FlightMainViewModel
import com.codingdrama.trippal.viewnodel.RateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val flightViewModel: FlightMainViewModel by viewModels()
    private val rateViewModel: RateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TripPalTheme {
                MainScreen(rateViewModel, flightViewModel)
            }
        }
    }

    @Composable
    fun MainScreen(viewModel: RateViewModel, flightViewModel: FlightMainViewModel) {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            val graph =
                navController.createGraph(startDestination = Screen.Flight.rout) {
                    composable(route = Screen.Flight.rout) {
                        FlightScreen(flightViewModel = flightViewModel)
                    }
                    composable(route = Screen.Rate.rout) {
                        RateScreen(rateViewModel = rateViewModel)
                    }
                }
            NavHost(
                navController = navController,
                graph = graph,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }

//    @Preview(showBackground = true)
//    @Composable
//    fun MainScreenPreview() {
//        TripPalTheme {
//            MainScreen()
//        }
//    }
}

