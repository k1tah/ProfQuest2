package com.example.profquest2.ui.screens.profile.auth.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.view.icon.Icon
import com.example.profquest2.ui.view.text.BodyText
import com.example.profquest2.ui.view.text.TitleText
import com.example.profquest2.ui.view.textField.PrimaryTextField
import com.example.profquest2.utils.showShortToast
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun CodeScreen(
    navController: NavController,
    email: String,
    password: String,
    viewModel: CodeViewModel = hiltViewModel()
) {
    var showRetryButton by rememberSaveable {
        mutableStateOf(false)
    }
    var time by rememberSaveable {
        mutableIntStateOf(30)
    }
    LaunchedEffect(showRetryButton) {
        repeat(30) {
            delay(1000L)
            time--
        }
        showRetryButton = true
    }

    var code by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is CodeSideEffect.Error -> context.showShortToast(it.message)

            is CodeSideEffect.Success -> {
                navController.navigate(Destination.Profile.route) {
                    popUpTo(Destination.Code.route) {
                        inclusive = true
                    }
                }
            }

            is CodeSideEffect.CodeResend -> {
                showRetryButton = false
                time = 30
            }
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Icon(
                icon = R.drawable.ic_arrow_left,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(1f))

        TitleText(
            text = stringResource(id = R.string.we_send_code),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        PrimaryTextField(
            value = code,
            hint = stringResource(id = R.string.code),
            onValueChange = {
                code = it
                if (code.length == 20) viewModel.signUp(email, password, code)
            },
            modifier = Modifier
                .width(128.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(showRetryButton) {
            Text(
                text = stringResource(id = R.string.send_code),
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.primary),
                modifier = Modifier.clickable { viewModel.sendCode(email) },
                textDecoration = TextDecoration.Underline
            )
        }

        AnimatedVisibility(!showRetryButton) {
            BodyText(text = "Повторная отправка возможна через $time сек")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}