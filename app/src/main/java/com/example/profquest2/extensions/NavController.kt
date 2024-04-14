package com.example.profquest2.extensions

import androidx.navigation.NavController
import androidx.navigation.navOptions

fun NavController.replaceAll(route: String) = navigate(route, navOptions { popBackStack() })