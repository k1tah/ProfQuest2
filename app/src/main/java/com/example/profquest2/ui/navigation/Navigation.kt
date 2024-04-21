package com.example.profquest2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.graph.Graph

object Navigation {
    val items = listOf(
        BottomNavBarItem(
            Graph.HomeGraph.route,
            R.drawable.ic_home_outl,
            R.drawable.ic_home,
            R.string.home
        ),
        BottomNavBarItem(
            Graph.TestGraph.route,
            R.drawable.ic_tests,
            R.drawable.ic_tests,
            R.string.tests
        ),
        BottomNavBarItem(
            Graph.VacanciesGraph.route,
            R.drawable.ic_vacansies,
            R.drawable.ic_vacansies,
            R.string.vacansies
        ),
        BottomNavBarItem(
            Graph.ProfileGraph.route,
            R.drawable.ic_profile_outl,
            R.drawable.ic_profile,
            R.string.profile
        )
    )


    @Composable
    fun isSelected(navController: NavHostController, route: String): Boolean {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        return currentDestination?.hierarchy?.any { it.route == route } == true
    }
}