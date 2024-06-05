package com.example.profquest2.ui.composables.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.model.Company
import com.example.domain.model.Vacancy
import com.example.profquest2.R
import com.example.profquest2.extensions.formatDate
import com.example.profquest2.ui.composables.button.PrimaryButton
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.post.PostIcon
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.LabelText
import com.example.profquest2.ui.composables.text.SubtitleText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun VacancyItem(
    vacancy: Vacancy,
    company: Company,
    onFavouriteClick: () -> Unit,
    onSendResume: () -> Unit
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface),
        elevation = CardDefaults.cardElevation(4.dp),
        border = if (vacancy.isStroke) BorderStroke(
            1.dp,
            Brush.linearGradient(listOf(Color.Red, Color.White))
        ) else null
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            if (vacancy.isStroke) {
                BodyText(text = "Рекомендуем:")
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                PostIcon(fileId = company.image?.id.toString())
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    TitleText(text = company.name)
                    Spacer(modifier = Modifier.height(2.dp))
                    LabelText(text = vacancy.date.formatDate() ?: "")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TitleText(text = vacancy.vacancyName)
            Spacer(modifier = Modifier.height(4.dp))

            Spacer(modifier = Modifier.height(16.dp))
            if (!expanded) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LabelText(
                        text = stringResource(R.string.show_more),
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        icon = if (vacancy.isFavourite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite,
                        modifier = Modifier.clickable { onFavouriteClick() }
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    SubtitleText(text = stringResource(R.string.description))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = vacancy.description)
                    Spacer(modifier = Modifier.height(8.dp))

                    SubtitleText(text = stringResource(R.string.contacts))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = vacancy.email)
                    Spacer(modifier = Modifier.height(8.dp))

                    LabelText(
                        text = stringResource(R.string.hide),
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LabelText(
                            text = stringResource(R.string.show_more),
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            icon = if (vacancy.isFavourite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite,
                            modifier = Modifier.clickable { onFavouriteClick() }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(onClick = { onSendResume() }, text = stringResource(R.string.send_resume))
        }
    }
}