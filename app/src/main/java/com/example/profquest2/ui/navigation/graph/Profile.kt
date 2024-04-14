package com.example.profquest2.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.profquest2.ui.MainViewModel
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.screens.profile.ProfileScreen
import com.example.profquest2.ui.screens.profile.SettingsScreen
import com.example.profquest2.ui.screens.profile.auth.resetpassword.ResetPasswordCodeScreen
import com.example.profquest2.ui.screens.profile.auth.resetpassword.ResetPasswordEmailScreen
import com.example.profquest2.ui.screens.profile.auth.signin.SignInScreen
import com.example.profquest2.ui.screens.profile.auth.signup.CodeScreen
import com.example.profquest2.ui.screens.profile.auth.resetpassword.ResetPasswordScreen
import com.example.profquest2.ui.screens.profile.auth.signup.SignUpScreen

fun NavGraphBuilder.profileGraph(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    navigation(
        route = Graph.ProfileGraph.route,
        startDestination = Destination.Profile.route
    ) {
        composable(Destination.Profile.route) {
            if (mainViewModel.getUserId() != -1L) {
                ProfileScreen(navController = navController)
            } else {
                SignInScreen(navController = navController)
            }
        }

        composable(Destination.Auth.route) {
            SignInScreen(navController)
        }

        composable(
            Destination.Code.route,
            arguments = listOf(
                navArgument(name = "email") { type = NavType.StringType },
                navArgument(name = "password") { type = NavType.StringType }
            )
        ) {
            val email = it.arguments?.getString("email")
            val password = it.arguments?.getString("password")
            if (!email.isNullOrBlank() && !password.isNullOrBlank()) CodeScreen(
                navController,
                email,
                password
            )
        }

        composable(Destination.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(
            Destination.ResetPassword.route + "/{email}/{code}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("code") { type = NavType.StringType })
        ) {
            ResetPasswordScreen(
                navController,
                email = it.arguments?.getString("email") ?: "",
                code = it.arguments?.getString("code") ?: ""
            )
        }

        composable(Destination.ResetPasswordEmail.route) {
            ResetPasswordEmailScreen(navController)
        }

        composable(
            Destination.ResetPasswordCode.route + "/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) {
            ResetPasswordCodeScreen(navController, it.arguments?.getString("email") ?: "")
        }

        composable(Destination.Settings.route) {
            SettingsScreen(navController, mainViewModel)
        }
    }
}