package com.example.profquest2.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.profquest2.ui.MainViewModel
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.screens.profile.auth.AuthScreen
import com.example.profquest2.ui.screens.profile.auth.EmailScreen
import com.example.profquest2.ui.screens.profile.auth.CodeScreen
import com.example.profquest2.ui.screens.profile.EditProfileScreen
import com.example.profquest2.ui.screens.profile.ProfileScreen
import com.example.profquest2.ui.screens.profile.auth.ResetPasswordScreen
import com.example.profquest2.ui.screens.profile.SettingsScreen

fun NavGraphBuilder.profileGraph(navController: NavController, mainViewModel: MainViewModel) {
    navigation(route = Graph.ProfileGraph.route, startDestination = Destination.Auth.route) {
        composable(Destination.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Destination.EditProfile.route) {
            EditProfileScreen(navController)
        }

        composable(Destination.Auth.route) {
            AuthScreen(navController)
        }

        composable(Destination.Code.route) {
            CodeScreen(navController)
        }

        composable(Destination.Email.route) {
            EmailScreen(navController)
        }

        composable(Destination.ResetPassword.route) {
            ResetPasswordScreen(navController)
        }

        composable(Destination.Settings.route) {
            SettingsScreen(navController, mainViewModel)
        }
    }
}