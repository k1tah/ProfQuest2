package com.example.profquest2.ui.screens.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.utils.SecondTestResults

@Composable
fun SecondTestResultsScreen(secondTestResults: SecondTestResults) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = secondTestResults.title,
            style = ProfQuest2Theme.typography.title.copy(
                color = ProfQuest2Theme.colors.onSurface,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = secondTestResults.description,
            style = ProfQuest2Theme.typography.body.copy(
                color = ProfQuest2Theme.colors.onSurface,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(48.dp))
    }
}