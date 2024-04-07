package com.example.profquest2.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.MainViewModel
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.composables.mswitch.PrimarySwitch
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun SettingsScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val mainState = mainViewModel.collectAsState().value
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                modifier = Modifier.clickable { navController.popBackStack() },
                tint = ProfQuest2Theme.colors.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.settings),
                style = ProfQuest2Theme.typography.title.copy(color = ProfQuest2Theme.colors.onSurface)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.dark_theme),
                style = ProfQuest2Theme.typography.title.copy(color = ProfQuest2Theme.colors.onSurface)
            )
            Spacer(modifier = Modifier.weight(1f))
            PrimarySwitch(checked = mainState.isDarkTheme, onCheckedChange = {
                mainViewModel.setTheme(it)
            })
        }
    }
}