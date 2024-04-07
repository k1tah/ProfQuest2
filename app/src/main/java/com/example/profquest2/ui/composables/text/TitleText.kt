package com.example.profquest2.ui.composables.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = ProfQuest2Theme.typography.title)
}