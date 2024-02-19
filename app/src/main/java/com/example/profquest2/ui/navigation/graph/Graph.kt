package com.example.profquest2.ui.navigation.graph

sealed class Graph(val route: String) {
    data object HomeGraph: Graph("homeGraph")

    data object TestGraph: Graph("testGraph")

    data object VacanciesGraph: Graph("vacanciesGraph")

    data object ProfileGraph: Graph("profileGraph")
}