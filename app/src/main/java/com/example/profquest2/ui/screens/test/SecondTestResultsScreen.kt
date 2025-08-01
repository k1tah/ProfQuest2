package com.example.profquest2.ui.screens.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.utils.SecondTestResults

@Composable
fun SecondTestResultsScreen(
    secondTestResults: SecondTestResults,
    testsViewModel: TestsViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        testsViewModel.setYovayshiTestResult(secondTestResults.title + secondTestResults.description)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        TitleText(text = secondTestResults.title)
        Spacer(modifier = Modifier.height(24.dp))

        BodyText(
            text = secondTestResults.description,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))
    }
}