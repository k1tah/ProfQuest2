package com.example.profquest2.ui.screens.profile.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.views.buttons.PrimaryButton
import com.example.profquest2.ui.views.textFields.ProfileInfoField

@Composable
fun AuthScreen(navController: NavController) {
    var login by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.sign_in),
            style = ProfQuest2Theme.typography.title.copy(color = ProfQuest2Theme.colors.onSurface)
        )
        Spacer(modifier = Modifier.height(24.dp))
        ProfileInfoField(
            label = stringResource(id = R.string.login),
            showLabel = false,
            value = login,
            onValueChange = {
                login = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfileInfoField(
            label = stringResource(id = R.string.password),
            showLabel = false,
            value = password,
            onValueChange = {
                password = it
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.forgot_password),
                style = ProfQuest2Theme.typography.label.copy(
                    fontSize = 14.sp,
                    color = ProfQuest2Theme.colors.primary
                ),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(end = 56.dp)
                    .clickable {
                        navController.navigate(Destination.Email.route)
                    }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryButton(
            onClick = {
                navController.navigate(Destination.Profile.route) {
                    popUpTo(Destination.Auth.route) {
                        inclusive = true
                    }
                }
            },
            text = stringResource(id = R.string.log_in),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
    }
}