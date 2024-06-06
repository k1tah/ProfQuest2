package com.example.profquest2.ui.screens.profile.auth.signin

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.composables.button.PrimaryButton
import com.example.profquest2.ui.composables.text.SubtitleText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.composables.textField.PrimaryTextField
import com.example.profquest2.utils.showShortToast
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = hiltViewModel()) {
    var login by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is SignInSideEffect.Error -> context.showShortToast(it.message)

            else -> {
                navController.navigate(Destination.Profile.route) {
                    popUpTo(Destination.Auth.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        TitleText(text = stringResource(id = R.string.sign_in))
        Spacer(modifier = Modifier.height(24.dp))

        PrimaryTextField(
            hint = stringResource(id = R.string.login),
            value = login,
            onValueChange = { login = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        PrimaryTextField(
            hint = stringResource(id = R.string.password),
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.forgot_password),
                style = ProfQuest2Theme.typography.subtitle.copy(color = ProfQuest2Theme.colors.primary),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(end = 56.dp)
                    .clickable { navController.navigate(Destination.ResetPasswordEmail.route) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            onClick = {
                viewModel.signIn(login, password)
            },
            text = stringResource(id = R.string.log_in),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            SubtitleText(text = stringResource(R.string.sign_up_hint))

            Text(
                text = stringResource(R.string.sign_up),
                style = ProfQuest2Theme.typography.subtitle.copy(color = ProfQuest2Theme.colors.primary),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Destination.SignUp.route)
                    }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}