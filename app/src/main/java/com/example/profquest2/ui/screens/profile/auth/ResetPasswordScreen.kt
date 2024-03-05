package com.example.profquest2.ui.screens.profile.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.view.button.PrimaryButton
import com.example.profquest2.ui.view.textField.ProfileInfoField

@Composable
fun ResetPasswordScreen(navController: NavController) {
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var passwordConfirmation by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                Modifier
                    .clickable { navController.popBackStack() }
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.refresh_password),
            style = ProfQuest2Theme.typography.title.copy(color = ProfQuest2Theme.colors.onSurface)
        )
        Spacer(modifier = Modifier.height(24.dp))
        ProfileInfoField(
            label = stringResource(id = R.string.new_password),
            showLabel = false,
            value = password,
            onValueChange = {
                password = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfileInfoField(
            label = stringResource(id = R.string.confirm_password),
            showLabel = false,
            value = passwordConfirmation,
            onValueChange = {
                passwordConfirmation = it
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryButton(
            onClick = {
                navController.navigate(Destination.Profile.route) {
                    popUpTo(Destination.Profile.route) {
                        inclusive = true
                    }
                }
            },
            text = stringResource(id = R.string.change_password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}