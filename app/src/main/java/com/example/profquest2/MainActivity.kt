package com.example.profquest2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.profquest2.ui.MainViewModel
import com.example.profquest2.ui.navigation.Navigation
import com.example.profquest2.ui.navigation.graph.Graph
import com.example.profquest2.ui.navigation.graph.homeGraph
import com.example.profquest2.ui.navigation.graph.profileGraph
import com.example.profquest2.ui.navigation.graph.testGraph
import com.example.profquest2.ui.navigation.graph.vacanciesGraph
import com.example.profquest2.ui.theme.ProfQuest2Theme
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val state = mainViewModel.collectAsState().value
            ProfQuest2Theme(darkTheme = state.isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = ProfQuest2Theme.images.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            NavHost(navController = navController, startDestination = Graph.HomeGraph.route) {
                homeGraph(navController = navController)
                testGraph(navController = navController)
                vacanciesGraph(navController = navController)
                profileGraph(navController = navController, viewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(backgroundColor = ProfQuest2Theme.colors.surface) {
        Navigation.items.forEach {
            val selected = Navigation.isSelected(navController = navController, route = it.route)
            BottomNavigationItem(
                alwaysShowLabel = false,
                selected = selected,
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = if (selected) it.selectedIcon else it.icon),
                        tint = if (selected) ProfQuest2Theme.colors.primary else ProfQuest2Theme.colors.tertiary,
                        contentDescription = stringResource(id = it.name),
                        modifier = if (selected) Modifier.padding(6.dp) else Modifier
                    )
                },
                label = {
                    AnimatedVisibility(visible = selected) {
                        Text(
                            text = stringResource(id = it.name),
                            color = ProfQuest2Theme.colors.primary
                        )
                    }
                }
            )
        }
    }
}