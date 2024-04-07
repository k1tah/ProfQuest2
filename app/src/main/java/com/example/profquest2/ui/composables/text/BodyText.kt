package com.example.profquest2.ui.composables.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Start) {
    Text(
        text = text,
        modifier = modifier,
        style = ProfQuest2Theme.typography.body.copy(textAlign = textAlign)
    )
}