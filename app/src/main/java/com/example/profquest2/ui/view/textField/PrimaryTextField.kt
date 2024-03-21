package com.example.profquest2.ui.view.textField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.view.text.LabelText

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable () -> Unit = {},
    label: String = "",
    hint: String = "",
    enabled: Boolean = true
) {
    Column {
        if (label.isNotBlank()) {
            Text(
                text = label,
                style = ProfQuest2Theme.typography.body.copy(color = ProfQuest2Theme.colors.onSurface),
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ProfQuest2Theme.colors.primary,
                unfocusedBorderColor = ProfQuest2Theme.colors.primary,
                focusedContainerColor = ProfQuest2Theme.colors.surface,
                unfocusedContainerColor = ProfQuest2Theme.colors.surface,
                disabledBorderColor = ProfQuest2Theme.colors.tertiary,
                disabledContainerColor = ProfQuest2Theme.colors.surface,
                disabledTextColor = ProfQuest2Theme.colors.onSurface
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            textStyle = ProfQuest2Theme.typography.body,
            placeholder = { LabelText(text = hint) },
            modifier = modifier,
            trailingIcon = { trailingIcon() },
            enabled = enabled
        )
    }
}