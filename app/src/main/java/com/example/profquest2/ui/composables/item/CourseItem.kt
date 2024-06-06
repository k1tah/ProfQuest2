package com.example.profquest2.ui.composables.item

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.model.Company
import com.example.domain.model.Course
import com.example.profquest2.R
import com.example.profquest2.extensions.formatDate
import com.example.profquest2.ui.composables.post.PostIcon
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.LabelText
import com.example.profquest2.ui.composables.text.SubtitleText
import com.example.profquest2.ui.composables.text.TitleText
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun CourseItem(
    course: Course
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ProfQuest2Theme.colors.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PostIcon(fileId = course.icon?.id.toString())
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    TitleText(text = course.uname)
                    Spacer(modifier = Modifier.height(2.dp))
                    LabelText(text = course.date.formatDate() ?: "")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TitleText(text = course.name)
            Spacer(modifier = Modifier.height(8.dp))

            BodyText(text = "Продолжительность: ${course.length}")
            Spacer(modifier = Modifier.height(8.dp))

            BodyText(text = "Стоимость: ${course.cost}")
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(16.dp))
            if (!expanded) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LabelText(
                        text = stringResource(R.string.show_more),
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    SubtitleText(text = stringResource(R.string.description))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = course.description)
                    Spacer(modifier = Modifier.height(8.dp))

                    SubtitleText(text = stringResource(R.string.contacts))
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = course.email)
                    Spacer(modifier = Modifier.height(4.dp))
                    BodyText(text = course.phone)
                    Spacer(modifier = Modifier.height(8.dp))

                    LabelText(
                        text = if (expanded) stringResource(id = R.string.hide) else stringResource(R.string.show_more),
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                }
            }
        }
    }
}