package com.example.profquest2.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.screens.test.SecondTestResultsScreen
import com.example.profquest2.ui.screens.test.SecondTestScreen
import com.example.profquest2.ui.screens.test.SelectTestScreen
import com.example.profquest2.ui.screens.test.TestResultsScreen
import com.example.profquest2.ui.screens.test.TestScreen
import com.example.profquest2.utils.Results
import com.example.profquest2.utils.SecondTestResults
import com.example.profquest2.utils.SecondTestResultsNavType
import com.example.profquest2.utils.TestResultsNavType

fun NavGraphBuilder.testGraph(navController: NavController) {
    navigation(route = Graph.TestGraph.route, startDestination = Destination.SelectTest.route) {
        composable(Destination.SelectTest.route) {
            SelectTestScreen(navController)
        }
        composable(Destination.Test.route) {
            TestScreen(navController)
        }
        composable(
            Destination.TestResults.route,
            arguments = listOf(navArgument("results") { type = TestResultsNavType() })
        ) {
            it.arguments?.getParcelable<Results>("results")
                ?.let { results ->
                    TestResultsScreen(results = results)
                }
        }
        composable(Destination.SecondTest.route) {
            SecondTestScreen(navController)
        }
        composable(
            Destination.SecondTestResults.route,
            arguments = listOf(navArgument("results") {
                type = SecondTestResultsNavType()
            })
        ) {
            it.arguments?.getParcelable<SecondTestResults>("results")
                ?.let { results ->
                    SecondTestResultsScreen(secondTestResults = results)
                }
        }
    }
}