package com.example.profquest2.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.screens.EditProfileScreen
import com.example.profquest2.ui.screens.ProfileScreen

fun NavGraphBuilder.profileGraph(navController: NavController) {
    navigation(route = Graph.ProfileGraph.route, startDestination = Destination.Profile.route) {
        composable(Destination.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Destination.EditProfile.route) {
            EditProfileScreen(navController)
        }
    }
}