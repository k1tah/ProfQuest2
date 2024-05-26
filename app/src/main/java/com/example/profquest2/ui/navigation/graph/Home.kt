package com.example.profquest2.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.screens.company.CompanyScreen
import com.example.profquest2.ui.screens.company.CompanyVacanciesScreen
import com.example.profquest2.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(route = Graph.HomeGraph.route, startDestination = Destination.Home.route) {
        composable(Destination.Home.route) {
            HomeScreen(navController)
        }

        composable(
            Destination.Company.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            CompanyScreen(navController, it.arguments?.getLong("id") ?: -1L)
        }

        composable(
            Destination.CompanyVacancies.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            CompanyVacanciesScreen(id = it.arguments?.getLong("id") ?: -1L, navController = navController)
        }
    }
}