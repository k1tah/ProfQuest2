package com.example.profquest2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.theme.ProfQuest2Theme
import com.example.profquest2.ui.view.icon.Icon
import com.example.profquest2.ui.view.text.BodyText
import com.example.profquest2.ui.view.text.LabelText
import com.example.profquest2.ui.view.text.SubtitleText
import com.example.profquest2.ui.view.text.TitleText

@Composable
fun CompanyScreen(navController: NavController) {
    var infoExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Icon(
            icon = R.drawable.ic_arrow_left,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(100)
                    )
                    .size(108.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                TitleText(text = "АО «НИИЭМП»")
                Spacer(modifier = Modifier.height(2.dp))
                LabelText(text = "@niiemp")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        BodyText(
            text = "Научно-исследовательский институт электронно-механических приборов",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon = R.drawable.ic_calendar,
                tint = ProfQuest2Theme.colors.secondary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            LabelText(text = "Присоединились в Январе 2024")
        }
        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon = R.drawable.ic_location,
                tint = ProfQuest2Theme.colors.secondary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "г Пенза, ул Каракозова, стр. 44",
                style = ProfQuest2Theme.typography.label.copy(
                    fontSize = 14.sp,
                    color = ProfQuest2Theme.colors.onSurface
                ),
                textDecoration = TextDecoration.Underline
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        icon = R.drawable.ic_link,
                        tint = ProfQuest2Theme.colors.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "t.me/niiemp",
                        style = ProfQuest2Theme.typography.label.copy(
                            fontSize = 14.sp,
                            color = ProfQuest2Theme.colors.primary
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        icon = R.drawable.ic_link,
                        tint = ProfQuest2Theme.colors.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "t.me/niiemp",
                        style = ProfQuest2Theme.typography.label.copy(
                            fontSize = 14.sp,
                            color = ProfQuest2Theme.colors.primary
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        icon = R.drawable.ic_link,
                        tint = ProfQuest2Theme.colors.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "t.me/niiemp",
                        style = ProfQuest2Theme.typography.label.copy(
                            fontSize = 14.sp,
                            color = ProfQuest2Theme.colors.primary
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
            Spacer(modifier = Modifier.width(32.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        icon = R.drawable.ic_phone,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "+79374212573",
                        style = ProfQuest2Theme.typography.body.copy(
                            fontSize = 14.sp,
                            color = ProfQuest2Theme.colors.onSurface
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        icon = R.drawable.ic_clock,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    BodyText(text = "Пн-пт: 08:00–17:00 Перерыв: 12:00–13:00")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            SubtitleText(text = "Электронная почта:")
            Spacer(modifier = Modifier.width(4.dp))
            BodyText(text = "aoNimp@bk.ru")
        }

        AnimatedVisibility(visible = infoExpanded) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                SubtitleText(text = "Краткая информация:")
                Spacer(modifier = Modifier.height(4.dp))

                BodyText(
                    text = "НИИЭМП. АО Пензенский научно-исследовательский институт электронно-механических приборов Основание 1959 Основатели СССР. Расположение, Пенза, ул. Каракозова, 44 Отрасль электроника",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                SubtitleText(text = "Основной вид деятельности:")
                Spacer(modifier = Modifier.height(4.dp))

                BodyText(
                    text = "Научные исследования и разработки в области естественных и технических наук прочие",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        LabelText(
            text = if (infoExpanded) "Свернуть" else "Показать больше",
            modifier = Modifier.clickable { infoExpanded = !infoExpanded }
        )
        Spacer(modifier = Modifier.height(8.dp))

        SubtitleText(
            text = "Посты",
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ProfQuest2Theme.colors.tertiary)
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) {
                CompanyNewsItem()
            }
        }
    }
}

@Composable
fun CompanyNewsItem() {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material.Icon(
                painter = painterResource(id = R.drawable.niiemp),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                TitleText(text = "НИИЭМП")
                Spacer(modifier = Modifier.height(2.dp))
                LabelText(text = "12.02.2024 09:00")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        BodyText(text = "О да! Для вас мы открылись!")
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}