package com.example.profquest2.ui.view.icon

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun Icon(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    tint: Color = ProfQuest2Theme.colors.onSurface
) {
    androidx.compose.material3.Icon(
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = modifier,
        tint = tint
    )
}
