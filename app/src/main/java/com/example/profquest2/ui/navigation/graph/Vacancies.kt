package com.example.profquest2.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.screens.VacanciesScreen

fun NavGraphBuilder.vacanciesGraph(navController: NavController) {
    navigation(route = Graph.VacanciesGraph.route, startDestination = Destination.Vacancies.route) {
        composable(Destination.Vacancies.route) { VacanciesScreen() }
    }
}