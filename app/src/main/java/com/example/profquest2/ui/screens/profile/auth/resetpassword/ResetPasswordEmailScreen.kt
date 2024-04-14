package com.example.profquest2.ui.screens.profile.auth.resetpassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.extensions.replaceAll
import com.example.profquest2.ui.composables.button.PrimaryButton
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.composables.textField.PrimaryTextField
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.utils.showShortToast
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ResetPasswordEmailScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    viewModel.collectSideEffect {
        when (it) {
            is ResetPasswordSideEffect.Error -> context.showShortToast(it.message)

            is ResetPasswordSideEffect.SuccessCodeSend -> navController.replaceAll(Destination.ResetPasswordCode.route + "/$email")

            else -> Unit
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

        TitleText(text = stringResource(id = R.string.email_for_reset))
        Spacer(modifier = Modifier.height(24.dp))

        PrimaryTextField(
            hint = stringResource(id = R.string.email),
            value = email,
            onValueChange = {
                email = it
            }
        )
        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            onClick = { viewModel.sendCode(email) },
            text = stringResource(id = R.string.send_code),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}