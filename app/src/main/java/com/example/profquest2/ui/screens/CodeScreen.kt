package com.example.profquest2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.navigation.Destination
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.views.textFields.PrimaryTextField
import kotlinx.coroutines.delay

@Composable
fun CodeScreen(navController: NavController) {
    var showRetryButton by rememberSaveable {
        mutableStateOf(false)
    }
    var time by rememberSaveable {
        mutableStateOf(30)
    }
    LaunchedEffect(showRetryButton) {
        repeat(30) {
            delay(1000L)
            time--
        }
        showRetryButton = true
    }
    var code1 by rememberSaveable {
        mutableStateOf("")
    }
    var code2 by rememberSaveable {
        mutableStateOf("")
    }
    var code3 by rememberSaveable {
        mutableStateOf("")
    }
    var code4 by rememberSaveable {
        mutableStateOf("")
    }
    var code5 by rememberSaveable {
        mutableStateOf("")
    }
    var code6 by rememberSaveable {
        mutableStateOf("")
    }
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }
    val focusRequester5 = remember { FocusRequester() }
    val focusRequester6 = remember { FocusRequester() }
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
            text = stringResource(id = R.string.we_send_code),
            style = ProfQuest2Theme.typography.title.copy(textAlign = TextAlign.Center),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            PrimaryTextField(
                value = code1,
                onValueChange = {
                    if (code1.isBlank()) code1 = it
                    focusRequester2.requestFocus()
                },
                modifier = Modifier
                    .focusRequester(focusRequester1)
                    .size(48.dp)
            )
            PrimaryTextField(
                value = code2,
                onValueChange = {
                    if (code2.isBlank()) code2 = it
                    focusRequester3.requestFocus()
                },
                modifier = Modifier
                    .focusRequester(focusRequester2)
                    .size(48.dp)
            )
            PrimaryTextField(
                value = code3,
                onValueChange = {
                    if (code3.isBlank()) code3 = it
                    focusRequester4.requestFocus()
                },
                modifier = Modifier
                    .focusRequester(focusRequester3)
                    .size(48.dp)
            )
            PrimaryTextField(
                value = code4,
                onValueChange = {
                    if (code4.isBlank()) code4 = it
                    focusRequester5.requestFocus()
                },
                modifier = Modifier
                    .focusRequester(focusRequester4)
                    .size(48.dp)
            )
            PrimaryTextField(
                value = code5,
                onValueChange = {
                    if (code5.isBlank()) code5 = it
                    focusRequester6.requestFocus()
                },
                modifier = Modifier
                    .focusRequester(focusRequester5)
                    .size(48.dp)
            )
            PrimaryTextField(
                value = code6,
                onValueChange = {
                    if (code6.isBlank()) code6 = it
                    navController.navigate(Destination.ResetPassword.route) {
                        popUpTo(Destination.Code.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .focusRequester(focusRequester6)
                    .size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        AnimatedVisibility(showRetryButton) {
            Text(
                text = stringResource(id = R.string.send_code),
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.colorPrimary),
                modifier = Modifier
                    .clickable {
                        showRetryButton = false
                        time = 5
                    },
                textDecoration = TextDecoration.Underline
            )
        }
        AnimatedVisibility(!showRetryButton) {
            Text(
                text = "Повторная отправка возможна через $time сек",
                style = ProfQuest2Theme.typography.body
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}