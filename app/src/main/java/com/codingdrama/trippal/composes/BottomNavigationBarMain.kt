package com.codingdrama.trippal.composes

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codingdrama.trippal.R

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

sealed class Screen(val rout: String) {
    data object Flight: Screen("flight_screen")
    data object Rate: Screen("rate_screen")
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, context: Context = LocalContext.current, navController: NavHostController) {
    val navigationItems = listOf(
        NavigationItem(
            title = LocalContext.current.resources.getString(R.string.title_flight),
            icon = ImageVector.vectorResource(id = R.drawable.icon_plane_24),
            route = Screen.Flight.rout
        ),
        NavigationItem(
            title = LocalContext.current.resources.getString(R.string.title_rate),
            icon = ImageVector.vectorResource(id = R.drawable.icon_rate_24),
            route = Screen.Rate.rout
        )
    )

    NavigationBar {
        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        val currentRoute = navBackStackEntry?.destination?.route

        // Bottom nav items we declared
        navigationItems.forEach { navItem ->
            // Place the bottom nav items
            NavigationBarItem(
                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },
                // Icon of navItem
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                },
                // label
                label = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}