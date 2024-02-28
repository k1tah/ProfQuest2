package com.example.profquest2.ui.views.buttons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun PrimaryButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = ProfQuest2Theme.colors.primary),
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = text)
    }
}