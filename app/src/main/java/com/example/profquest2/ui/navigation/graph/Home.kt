package com.example.profquest2.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.screens.CompanyScreen
import com.example.profquest2.ui.home.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(route = Graph.HomeGraph.route, startDestination = Destination.Home.route) {
        composable(Destination.Home.route) {
            HomeScreen(navController)
        }

        composable(Destination.Company.route) {
            CompanyScreen(navController)
        }
    }
}