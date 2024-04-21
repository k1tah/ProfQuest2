package com.example.profquest2.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.screens.schools.SchoolScreen
import com.example.profquest2.ui.screens.schools.SchoolsScreen
import com.example.profquest2.ui.screens.vacancies.VacanciesScreen

fun NavGraphBuilder.vacanciesGraph(navController: NavController) {
    navigation(route = Graph.VacanciesGraph.route, startDestination = Destination.Vacancies.route) {
        composable(Destination.Vacancies.route) { VacanciesScreen(navController = navController) }

        composable(Destination.Schools.route) { SchoolsScreen(navController = navController) }

        composable(
            Destination.School.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            SchoolScreen(navController = navController, id = it.arguments?.getLong("id") ?: -1L)
        }
    }
}