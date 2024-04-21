package com.example.profquest2.ui.composables.post

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.profquest2.R
import com.example.profquest2.ui.composables.icon.Icon
import com.example.profquest2.ui.composables.text.BodyText
import com.example.profquest2.ui.composables.text.LabelText
import com.example.profquest2.ui.theme.ProfQuest2Theme

@Composable
fun SurveyItem(
    text: String,
    votesCount: Int,
    isSelected: Boolean,
    onSelect: () -> Unit,
    percent: Float
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 8.dp)
            .height(48.dp)
            .border(1.dp, ProfQuest2Theme.colors.secondary, shape = RoundedCornerShape(4.dp))
    ) {
        AnimatedVisibility(
            visible = isSelected,
            modifier = Modifier.align(Alignment.CenterStart),
            enter = expandHorizontally()
        ) {
            Surface(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                ProfQuest2Theme.colors.tertiary,
                                ProfQuest2Theme.colors.surface
                            )
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .fillMaxWidth(percent)
                    .height(48.dp),
                content = {},
                color = Color.Transparent
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            BodyText(text = text)
            Spacer(modifier = Modifier.weight(1f))
            if (isSelected) BodyText(text = votesCount.toString())
        }
    }
}

@Composable
fun Survey(
    questions: List<String>,
    votes: List<Int>,
    isSelected: Boolean,
    onSelect: (Int) -> Unit,
    isExpired: Boolean,
    daysLeft: Int
) {
    Column {
        Icon(icon = R.drawable.ic_stats)
        Spacer(modifier = Modifier.height(16.dp))

        questions.forEachIndexed { index, item ->
            SurveyItem(
                text = item,
                votesCount = votes[index],
                isSelected = isExpired || isSelected,
                percent = if (votes.sum() != 0) (votes[index].toFloat() / votes.sum()
                    .toFloat()) else 0f,
                onSelect = { if (!isExpired) onSelect(index) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .background(color = ProfQuest2Theme.colors.secondary)
                .height(1.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabelText(text = votes.sum().toString() + stringResource(R.string.votes))
            Icon(icon = R.drawable.ic_circle)
            LabelText(
                text = when (daysLeft) {
                    in 1..Int.MAX_VALUE -> daysLeft.toString() + stringResource(R.string.vote_days_left)

                    0 -> stringResource(R.string.vote_last_day)

                    else -> stringResource(R.string.vote_end)
                }
            )
        }
    }
}