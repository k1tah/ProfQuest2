package com.example.profquest2.ui.composables.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = ProfQuest2Theme.colors.primary)
    }
}