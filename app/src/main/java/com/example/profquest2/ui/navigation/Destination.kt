package com.example.profquest2.ui.navigation

sealed class Destination(val route: String) {
    data object Home: Destination("home")

    data object Company: Destination("company")

    data object Vacancies: Destination("vacancies")

    data object SelectTest: Destination("selectTest")

    data object Test: Destination("test")

    data object SecondTest: Destination("secondTest")

    data object TestResults: Destination("testResults/{results}")

    data object SecondTestResults: Destination("secondTestResults/{results}")

    data object Profile: Destination("profile")

    data object EditProfile: Destination("editProfile")
}