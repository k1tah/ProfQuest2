package com.example.profquest2.ui.view.mswitch

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun PrimarySwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            uncheckedThumbColor = Color.White,
            checkedBorderColor = Color.Transparent,
            uncheckedBorderColor = Color.Transparent,
            checkedTrackColor = ProfQuest2Theme.colors.primary,
            uncheckedTrackColor = ProfQuest2Theme.colors.tertiary
        )
    )
}