package com.example.profquest2.ui.composables.button

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.runtime.Composable
import com.example.profquest2.ui.composables.text.SubtitleText
import com.example.profquest2.ui.theme.darkBlue
import com.example.profquest2.ui.theme.gray
import com.example.profquest2.ui.theme.grayMed
import com.example.profquest2.ui.theme.red

@Composable
fun PrimaryRadioButton(state: Boolean, onClick: () -> Unit, text: String){

    SubtitleText(text = text)
    RadioButton(selected = state, onClick = onClick,
        colors = RadioButtonColors(
            unselectedColor = darkBlue,
            selectedColor = red,
            disabledSelectedColor = grayMed,
            disabledUnselectedColor = gray
        )
    )
}