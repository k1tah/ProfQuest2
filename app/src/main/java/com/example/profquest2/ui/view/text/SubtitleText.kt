package com.example.profquest2.ui.view.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun SubtitleText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = ProfQuest2Theme.typography.subtitle)
}