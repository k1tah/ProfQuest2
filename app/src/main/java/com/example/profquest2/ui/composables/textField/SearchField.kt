package com.example.profquest2.ui.composables.textField

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.profquest2.R
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.text.LabelText

@Composable
fun SearchField(value: String, onValueChanged: (String) -> Unit, onClose: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            value = value,
            onValueChange = onValueChanged,
            leadingIcon = {
                Icon(
                    icon = R.drawable.ic_search,
                    modifier = Modifier.clickable { onClose() }
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = ProfQuest2Theme.colors.primary
            ),
            placeholder = {
                LabelText(text = stringResource(id = R.string.search))
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )
    }
}
